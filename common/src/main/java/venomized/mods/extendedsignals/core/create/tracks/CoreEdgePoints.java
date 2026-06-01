package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.graph.EdgePointType;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

public final class CoreEdgePoints {
    public static final EdgePointType<RepeaterSignal> REPEATER = EdgePointType.register(
            ExtendedSignalsCore.res("repeater"), RepeaterSignal::new
    );
    public static final EdgePointType<SpeedModifier> SPEED_MODIFIER = EdgePointType.register(
            ExtendedSignalsCore.res("speed_modifier"), SpeedModifier::new
    );
    public static EdgePointType<PathTrainDetector> PATH_TRAIN_DETECTOR = EdgePointType.register(
            ExtendedSignalsCore.res("path_train_detector"), PathTrainDetector::new
    );

    public static void init() {
    }
}
