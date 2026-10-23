package venomized.mods.extendedsignals.core.create.tracks.points;


import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.TrackNode;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import javax.annotation.Nullable;

public interface IExtendedEdgePoint {
    Component getName();

    default boolean facingDirections(boolean primary, TrackNode node) {
        return false;
    }

    default void onSignalScout(Direction.AxisDirection direction, @Nullable SignalStateNode newState, final Train train, double distance) {
    }

    default void onSignalCrossedImmediate(Direction.AxisDirection direction, @Nullable SignalStateNode signalState, Train train) {
    }

    default void onSignalCrossedEarly(Direction.AxisDirection direction, @Nullable SignalStateNode signalState, Train train) {
    }

    default void onSignalCrossedLate(Direction.AxisDirection axisDirection, @Nullable SignalStateNode signalState, Train train) {
    }
}
