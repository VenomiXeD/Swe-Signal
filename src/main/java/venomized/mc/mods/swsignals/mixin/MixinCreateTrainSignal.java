package venomized.mc.mods.swsignals.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;

@Mixin(SignalBlockEntity.class)
public abstract class MixinCreateTrainSignal implements ISignalTunerBindable {
	@Override
	public boolean isDestination() {
		return false;
	}
}
