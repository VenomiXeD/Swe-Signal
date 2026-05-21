package venomized.mods.extendedsignals.create;


import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.Direction;

import java.util.UUID;

public interface IExtendedSignalBoundary {
    UUID extendedSignal$getSignalBoundaryID();

    void extendedSignal$onScout(Direction.AxisDirection direction, Train train);

    void extendedSignal$onCrossed(Train train);
}
