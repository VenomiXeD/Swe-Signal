package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import venomized.mods.extendedsignals.core.create.tracks.points.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;

public interface ISignalModifier {
    void applyModifier(SignalStateNode stateToBeModified);

    TrackEdgePointSignalModifier.ModifierAction onAction(boolean primary, List<CollectedSignal> points, Train train);

    enum ModifierAction {
        NONE,
        APPLY,
        DISCARD
    }
}
