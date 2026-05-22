package venomized.mods.extendedsignals.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.RawSignalState;
import venomized.mods.extendedsignals.create.IExtendedSignalBoundary;

import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedSignalBoundary {
    @Unique
    private UUID extendedSignals$signalReservedForGroup;

    @Override
    public void extendedSignal$onScout(final Direction.AxisDirection direction, final Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(getId(),
                        new RawSignalState()
                                .setProceed(true)
                                .setAxisDirection(direction)
                );
    }

    /**
     * @param train Train crossing over
     */
    @Override
    public void extendedSignal$onCrossed(Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(getId(),
                        new RawSignalState()
                                .setProceed(false));
    }
}
