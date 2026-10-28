package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackNode;

public interface INavigation {
    double extendedSignals$speedMod();

    TrackNode extendedSignals$startScoutNode1();

    TrackNode extendedSignals$startScoutNode2();

    TrackEdge extendedSignals$startScoutEdge();

    double extendedSignals$startScoutPosition();

    static INavigation from(Train train) {
        return (INavigation) train.navigation;
    }
}
