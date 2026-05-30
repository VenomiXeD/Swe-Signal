package venomized.mods.extendedsignals.core.util;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.util.Mth;

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

    public static double absoluteTopSpeedForTrainsMs() {
        return Mth.absMax(
                AllConfigs.server().trains.poweredTrainTopSpeed.get(),
                AllConfigs.server().trains.trainTopSpeed.get()
        );
    }

    public static double absoluteTopSpeedForTrainsKph() {
        return MathHelp.KphFromMs(absoluteTopSpeedForTrainsMs());
    }
}
