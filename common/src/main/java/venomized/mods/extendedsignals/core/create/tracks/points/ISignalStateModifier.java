package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;

public interface ISignalStateModifier {
    void applyModifier(SignalStateNode stateToBeModified);

    TrackEdgePointSignalModifier.ModifierAction onAction(boolean primary, List<CollectedEdgePoint> points, Train train);

    enum ModifierAction {
        NONE,
        APPLY,
        DISCARD
    }
}
