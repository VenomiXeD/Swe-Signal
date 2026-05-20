package venomized.mods.extendedsignals.create;


import com.simibubi.create.content.trains.entity.Train;

import java.util.UUID;

public interface IExtendedSignalBoundary {
    UUID extendedSignal$getSignalBoundaryID();

    void extendedSignal$onScout(Train train);

    void extendedSignal$onCrossed(Train train);
}
