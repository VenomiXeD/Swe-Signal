package venomized.mods.extendedsignals.core.mixin_interfaces;

import com.simibubi.create.content.trains.entity.Train;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ISignalEdgeGroup {
    @Nullable
    UUID extendedSignals$reservedByTrain();

    void extendedSignals$setReservedByTrain(Train train);

    default boolean extendedSignals$isReservedByOtherTrain(Train train) {
        UUID reservedBy = extendedSignals$reservedByTrain();
        return reservedBy != null && !reservedBy.equals(train.id);
    }
}
