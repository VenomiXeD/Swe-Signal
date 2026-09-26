package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface ISignal<T extends TrackEdgePoint> extends IExtendedEdgePoint<T> {
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
        ExtendedSignals.serverNetworkCache().updateState(
                ((TrackEdgePoint) this).getId(),
                direction == Direction.AxisDirection.POSITIVE, newState
        );
    }

    /**
     * @param direction
     * @param train
     */
    @Override
    default void onSignalCrossedEarly(Direction.AxisDirection direction, Train train) {
        ExtendedSignals.serverNetworkCache()
                .modifyState(((TrackEdgePoint) this).getId(),
                        direction == Direction.AxisDirection.POSITIVE,
                        state -> state.setProceed(false).setReservedBy(null)
                );
    }

    /**
     * @param direction
     * @param train
     */
    @Override
    default void onSignalCrossedLate(Direction.AxisDirection direction, Train train) {
    }

    default SignalStateNode currentSignalState(boolean front) {
        return ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY.getSignalState(((TrackEdgePoint) this).id, front);
    }
}
