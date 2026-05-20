package venomized.mods.extendedsignals.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mods.extendedsignals.blockentity.ISignalTunerBindable;

@Mixin(SignalBlockEntity.class)
public abstract class MixinCreateTrainSignal implements ISignalTunerBindable {
    @Override
    public boolean isReader() {
        return false;
    }
}
