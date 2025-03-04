package venomized.mc.mods.swsignals.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.world.InteractionResult;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;

import java.util.Optional;

@Mixin(SignalBlockEntity.class)
public abstract class MixinCreateTrainSignal implements ISignalTunerBindable {
	@Override
	public boolean isDestination() {
		return false;
	}
}
