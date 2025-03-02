package venomized.mc.mods.swsignals.mixin;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;

import java.util.Optional;

@Mixin(SignalBlockEntity.class)
public abstract class MixinCreateTrainSignal implements ISignalTunerBindable {
	/**
	 * Called on the target block entity;
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity
	 * @param mode
	 */
	@Override
	public void onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {

	}

	/**
	 * Called on the source block entity;
	 * Signal Box A -> Create Signal; Signal Box A is the target
	 *
	 * @param targetBlockEntity
	 * @param mode
	 */
	@Override
	public void onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode) {
	}
}
