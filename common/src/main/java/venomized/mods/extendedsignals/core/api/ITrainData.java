package venomized.mods.extendedsignals.core.api;

import com.google.common.collect.Lists;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.stream.Stream;

public interface ITrainData {
    int carriageCount();

    EdgePointCollection<TrackEdgePoint> passedEdgePoints();

    EdgePointCollection<TrackEdgePoint> scoutedEdgePoints();

    default EdgePointCollection<TrackEdgePoint> pathEdgePoints() {
        return new EdgePointCollection<>(Stream.concat(passedEdgePoints().stream(), scoutedEdgePoints().stream()).toList());
    }
}
