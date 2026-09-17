package venomized.mods.extendedsignals.core.create.tracks.points;


import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface IExtendedEdgePoint<T extends TrackEdgePoint> {
    default void onSignalScout(Direction.AxisDirection direction, SignalStateNode newState, final Train train, double distance) {
    }

    default void onSignalCrossedEarly(Direction.AxisDirection direction, Train train) {
    }

    default void onSignalCrossedLate(Direction.AxisDirection axisDirection, Train train) {
    }
}
