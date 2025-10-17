package venomized.mc.mods.swsignals.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

/**
 * Holds a reference to a block entity at a given position
 */
public final class BlockEntityReference<T extends BlockEntity> {
    private final Class<T> compatibleType;
    private final String saveTag;

    private BlockPos posRef;

    public BlockEntityReference(Class<T> compatibleType, String saveTag) {
        this.saveTag = saveTag;
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


    public T getReference(BlockEntity world) {
        return getReference(world.getLevel());
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

    public void toNBT(CompoundTag pTag) {
        pTag.put(saveTag, NbtUtils.writeBlockPos(this.posRef));
    }

    public void fromNBT(CompoundTag pTag) {
        posRef = NbtUtils.readBlockPos(pTag.getCompound(saveTag));
    }
}
