package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.graph.EdgePointType;
import venomized.mods.extendedsignals.core.ExtendedSignals;

public final class CoreEdgePoints {
    public static final EdgePointType<RepeaterSignalEdgePoint> REPEATER = EdgePointType.register(
            ExtendedSignals.res("repeater"), RepeaterSignalEdgePoint::new
    );
    public static final EdgePointType<LineSpeedModifier> LINE_SPEED_MODIFIER = EdgePointType.register(
            ExtendedSignals.res("line_speed_modifier"), LineSpeedModifier::new
    );
    public static EdgePointType<PathTrainDetector> PATH_TRAIN_DETECTOR = EdgePointType.register(
            ExtendedSignals.res("path_train_detector"), PathTrainDetector::new
    );
    public static EdgePointType<PathIdentifierEdgePoint> PATH_IDENTIFIER = EdgePointType.register(
            ExtendedSignals.res("path_identifier"), PathIdentifierEdgePoint::new
    );
    public static EdgePointType<LocalSpeedModifier> LOCAL_SPEED_MODIFIER = EdgePointType.register(
            ExtendedSignals.res("local_speed_modifier"), LocalSpeedModifier::new
    );

    public static void init() {
    }
}
