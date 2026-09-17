package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;

public interface ITrainSpeedModifier {
    void applySpeed(boolean front, Train train);
}
