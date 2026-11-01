package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.simibubi.create.content.trains.graph.EdgePointStorage;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import venomized.mods.extendedsignals.core.mixin.AccessorTrackGraph;

import java.util.List;

public interface IEdgePointStorage {
    List<TrackEdgePoint> listEdgePoints();

    static IEdgePointStorage from(TrackGraph trackGraph) {
        return cast(((AccessorTrackGraph) trackGraph).getEdgePoints());
    }

    static IEdgePointStorage cast(EdgePointStorage edgePointStorage) {
        return (IEdgePointStorage) edgePointStorage;
    }
}
