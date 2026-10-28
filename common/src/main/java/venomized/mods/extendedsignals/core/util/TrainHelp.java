package venomized.mods.extendedsignals.core.util;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.mixin.AccessorSignalEdgeGroup;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;

import java.util.Optional;

public class TrainHelp {
    public static double trainSpeedPercentFromKph(final double speedInKph, Train train, boolean manual) {
        return trainSpeedPercentFromMs(MathHelp.MsFromKph(speedInKph), train, manual);
    }

    private static double speedFactorIfManual(boolean manual) {
        return manual ? AllConfigs.server().trains.manualTrainSpeedModifier.get() : 1d;
    }

    public static double trainSpeedPercentFromMs(final double speedInMs, Train train, boolean manual) {
        final double speedInMpt = speedInMs / 20d; // Meter/tick
        return Mth.clamp(speedInMpt / (train.maxSpeed() * speedFactorIfManual(manual)), 0, 1);
    }

    public static double trainSpeedKphFromPercent(final double percent, Train train, boolean manual) {
        return MathHelp.KphFromMs(trainSpeedMsFromPercent(percent, train, manual));
    }

    public static double trainSpeedMsFromPercent(final double percent, Train train, boolean manual) {
        return train.maxSpeed() * percent * 20d * (speedFactorIfManual(manual));
    }

    public static void updateSpeed(Train train, double speedInKph) {
        double old = train.throttle;
        if (!ITrain.cast(train).isManual())
            train.throttle = TrainHelp.trainSpeedPercentFromKph(speedInKph, train, train.manualTick);
        if (train.throttle <= 0) {
            ExtendedSignals.LOGGER.warn("Point set speed to 0 for {}; Something is not acting properly... Throttle remains unchanged", train.name.getString());
            train.throttle = old;
        } else {
            train.carriages.forEach(c -> {
                c.forEachPresentEntity(e -> {
                    e.getControllingPlayer().map(e.level()::getPlayerByUUID).ifPresent(player -> {
                        player.sendSystemMessage(Component.literal("speed: %.2f km/h".formatted(speedInKph)));
                    });
                });
            });
        }
    }

    public static double absoluteTopSpeedForTrainsMs() {
        return Mth.absMax(
                AllConfigs.server().trains.poweredTrainTopSpeed.get(),
                AllConfigs.server().trains.trainTopSpeed.get()
        );
    }

    public static double absoluteTopSpeedForTrainsKph() {
        return MathHelp.KphFromMs(absoluteTopSpeedForTrainsMs());
    }


    public static boolean isReservedUnless(SignalEdgeGroup group, Train train) {
        if (group.intersectingResolved.isEmpty()) {
            ((AccessorSignalEdgeGroup) group).extendedSignals$walkIntersecting(group.intersectingResolved::add);
        }

        for (SignalEdgeGroup intersecting : group.intersectingResolved) {
            if (((ISignalEdgeGroup) intersecting)
                    .extendedSignals$isReservedByOtherTrain(train)) {
                return true;
            }
        }

        return false;
    }
}
