package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.graph.EdgePointType;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.tracks.points.PathTrainDetector;
import venomized.mods.extendedsignals.core.create.tracks.points.RepeaterSignal;
import venomized.mods.extendedsignals.core.create.tracks.points.SpeedModifier;

public final class CoreEdgePoints {
    public static final EdgePointType<RepeaterSignal> REPEATER = EdgePointType.register(
            ExtendedSignals.res("repeater"), RepeaterSignal::new
    );
    public static final EdgePointType<SpeedModifier> SPEED_MODIFIER = EdgePointType.register(
            ExtendedSignals.res("speed_modifier"), SpeedModifier::new
    );
    public static EdgePointType<PathTrainDetector> PATH_TRAIN_DETECTOR = EdgePointType.register(
            ExtendedSignals.res("path_train_detector"), PathTrainDetector::new
    );

    public static void init() {
    }
}
