package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface ISignal<T extends TrackEdgePoint> extends IExtendedEdgePoint {
    boolean isMainSignal(boolean front);

    void setMainSignal(boolean front, boolean mainSignal);

    boolean isBlockSignalMode(boolean front);

    void setBlockSignalMode(boolean front, boolean blockSignalMode);

    /**
     * @param direction
     * @param newState
     * @param train
     * @param distance
     */
    @Override
    default void onSignalScout(Direction.AxisDirection direction, SignalStateNode newState, final Train train, double distance) {
        ExtendedSignals.serverNetworkCache().synchronize(newState);
    }

    /**
     * @param direction
     * @param train
     */
    @Override
    default void onSignalCrossedEarly(Direction.AxisDirection direction, Train train) {
        SignalStateNode state = ExtendedSignals.serverNetworkCache()
                .prepareModification(((TrackEdgePoint) this).getId(), direction == Direction.AxisDirection.POSITIVE)
                .setProceed(false)
                .setReservedBy(null);

        ExtendedSignals.serverNetworkCache().synchronize(state);
    }

    /**
     * @param direction
     * @param train
     */
    @Override
    default void onSignalCrossedLate(Direction.AxisDirection direction, Train train) {
    }

    default SignalStateNode currentSignalState(boolean front) {
        return SignalStateNode.fromCache(((TrackEdgePoint) this).id, front);
    }
}
