package venomized.mc.mods.swsignals.blockentity;

import net.minecraftforge.common.extensions.IForgeBlockEntity;

import java.util.Optional;

public interface ISignalTunerBindable extends IForgeBlockEntity {
	default void onStartBinding() {
	}

	default boolean isSource() {
		return true;
	}

	default boolean isTarget() {
		return true;
	}

	/**
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity source block destination
	 * @param mode
	 */
	void onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode);

	/**
	 * Signal Box A -> Create Signal; Signal Box A is the target
	 *
	 * @param targetBlockEntity target block destination
	 * @param mode
	 */
	void onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode);

	enum SignalTunerMode {
		DISCONNECT_ALL,
		DISCONNECT,
		CONNECT,
		CONFIGURE
	}
}
