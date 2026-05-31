package venomized.mods.extendedsignals.core.create.tracks;


import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.Objects;
import java.util.UUID;

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

    void setMapper(boolean front, SignalStateRemapper mapper);

    boolean doSkipChaining(Direction.AxisDirection direction, Train train);

    default void setChainingSkipped(boolean front, boolean skipChaining) {
        throw new UnsupportedOperationException("Missing implementation for setting chaining");
    }

    default boolean getChainingSkipped(boolean front) {
        throw new UnsupportedOperationException("Missing implementation for getting chaining");
    }

    default void onSignalScout(Direction.AxisDirection direction, SignalStateNode newState, final Train train) {
        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level).updateState(
                ((T) this).getId(),
                direction == Direction.AxisDirection.POSITIVE,
                newState.setAxisDirection(direction)
        );
    }

    default void onSignalCrossed(Direction.AxisDirection direction, Train train) {
        if (train.speed == 0)
            return;

        Entity entity = train.carriages.get(0).anyAvailableEntity();
        if (entity == null)
            return;

        Level level = entity.level();
        if (level.isClientSide()) {
            return;
        }

        ExtendedSignalsCore.sidedNetwork(level)
                .updateState(((T) this).getId(),
                        direction == Direction.AxisDirection.POSITIVE,
                        SignalStateNode.STOP
                );
    }

    UUID pointId();
}
