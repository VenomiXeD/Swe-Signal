package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;

import java.util.UUID;

@Mixin(value = SignalEdgeGroup.class, remap = false)
public abstract class MixinSignalEdgeGroup implements ISignalEdgeGroup {
    @Unique
    private UUID extendedSignals$edgeGroupReservedFor;

    /**
     * @param train
     */
    @Override
    public void extendedSignals$setReservedByTrain(Train train) {
        extendedSignals$edgeGroupReservedFor = train == null ? null : train.id;
    }

    /**
     * @return
     */
    @Override
    public @Nullable UUID extendedSignals$reservedByTrain() {
        return extendedSignals$edgeGroupReservedFor;
    }
}
