package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;

import java.util.List;

public interface ITrainSpeedModifier {
    void applySpeed(boolean front, List<CollectedEdgePoint> points, Train train);
}
