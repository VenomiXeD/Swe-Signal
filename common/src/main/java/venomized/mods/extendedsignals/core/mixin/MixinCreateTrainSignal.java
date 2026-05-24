package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;

import java.util.UUID;

@Mixin(value = SignalBlockEntity.class, remap = false)
public abstract class MixinCreateTrainSignal implements ISignalTu   nerToolable,ISignalBoundaryReferenceProvider {
    @Override
    public boolean isReader() {
        return false;
    }


    /**
     * @return
     */
    @Override
    public UUID id() {
        return ((SignalBlockEntity) (Object) this).getSignal().getId();
    }

    /**
     * @return
     */
    @Override
    public Direction.AxisDirection direction() {
        return ((SignalBlockEntity) (Object) this).edgePoint.getTargetDirection();
    }
}
