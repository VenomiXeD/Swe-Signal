package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface ISignal extends IExtendedEdgePoint {
    boolean isMainSignal(boolean front);

    default boolean isDistantSignal(boolean front) {
        return true;
    } // TODO: Allow signals to determine if they're a distant signal too

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
     * @param signalState
     * @param train
     */
    @Override
    default void onSignalCrossedEarly(Direction.AxisDirection direction, SignalStateNode signalState, Train train) {
        if (signalState == null)
            return;

        ExtendedSignals.serverNetworkCache().synchronize(signalState.setProceed(false).setReservedBy(null));
    }

    /**
     * @param direction
     * @param signalState
     * @param train
     */
    @Override
    default void onSignalCrossedLate(Direction.AxisDirection direction, SignalStateNode signalState, Train train) {
    }

    default SignalStateNode currentSignalState(boolean front) {
        return SignalStateNode.fromCache(((TrackEdgePoint) this).id, front);
    }
}
