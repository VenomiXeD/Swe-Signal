package venomized.mods.extendedsignals.core.create.tracks;


import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import java.util.Objects;

public interface IExtendedSignalBoundary<T extends TrackEdgePoint> {
    /**
     * {@link net.minecraft.core.Direction.AxisDirection#POSITIVE} Configuration
     *
     * @return
     */
    default SignalBoundaryConfiguration getPositiveSignalBoundaryConfiguration() {
        return new SignalBoundaryConfiguration();
    }

    /**
     * {@link net.minecraft.core.Direction.AxisDirection#NEGATIVE} Configuration
     *
     * @return
     */
    default SignalBoundaryConfiguration getNegativeSignalBoundaryConfiguration() {
        return new SignalBoundaryConfiguration();
    }

    boolean skipChaining();

    default void onSignalScout(final Direction.AxisDirection direction, RawSignalState newState, final Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level).updateState(((TrackEdgePoint) this).getId(), Objects.requireNonNullElse(
                newState,
                new RawSignalState().setAxisDirection(direction)
        ));
    }

    default void onSignalCrossed(Direction.AxisDirection direction, Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(((T) this).getId(),
                        new RawSignalState()
                                .setProceed(false)
                                .setReserved(false)
                                .setAxisDirection(direction)
                );
    }
}
