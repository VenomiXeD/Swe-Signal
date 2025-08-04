package venomized.mc.mods.swsignals.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

/**
 * Holds a reference to a block entity at a given position
 */
public final class BEPosRef<T extends BlockEntity> {
	private final Class<T> compatibleType;

	private BlockPos posRef;

	public BEPosRef(Class<T> compatibleType) {
		this.compatibleType = compatibleType;
	}

	public T getReference(BlockGetter world) {
		if (this.posRef == null) {
			return null;
		}
		BlockEntity be = world.getBlockEntity(posRef);
		if (valid(be)) {
			return (T) be;
		}
		return null;
	}

	public Optional<T> getOptionalReference(BlockGetter world) {
		return Optional.ofNullable(getReference(world));
	}

	public boolean referenceValid(BlockGetter world) {
		BlockEntity be = world.getBlockEntity(posRef);
		return valid(be);
	}

	public boolean valid(BlockEntity be) {
		return compatibleType.isInstance(be);
	}

	public void newTarget(BlockPos newBlockPosTarget) {
		this.posRef = newBlockPosTarget;
	}

	public void newTarget(T newBlockEntityTarget) {
		this.posRef = newBlockEntityTarget.getBlockPos();
	}
}
