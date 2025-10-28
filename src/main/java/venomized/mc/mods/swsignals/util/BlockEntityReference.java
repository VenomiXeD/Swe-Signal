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

    public Optional<T> getReference(BlockGetter world) {
        if (this.posRef == null) {
            return null;
        }
        BlockEntity be = world.getBlockEntity(posRef);
        return valid(be) ? Optional.of((T) be) : Optional.empty();
    }


    public Optional<T> getReference(BlockEntity blockEntity) {
        return getReference(blockEntity.getLevel());
    }

    public boolean referenceValid(BlockGetter world) {
        BlockEntity be = world.getBlockEntity(posRef);
        return valid(be);
    }

    public boolean valid(BlockEntity be) {
        return compatibleType.isInstance(be);
    }

    /**
     * @param newBlockPosTarget
     * @apiNote
     */
    public void newTarget(BlockPos newBlockPosTarget) {
        this.posRef = newBlockPosTarget;
    }

    /**
     * Specifies a new target block entity (with block entity compatibility validation)
     *
     * @param newBlockEntityTarget
     * @return True if successfully applied
     */
    public boolean newTarget(T newBlockEntityTarget) {
        if (newBlockEntityTarget == null) {
            this.posRef = null;
            return true;
        }

        if (!valid(newBlockEntityTarget)) {
            return false;
        }

        this.posRef = newBlockEntityTarget.getBlockPos();
        return true;
    }

    public void toNBT(CompoundTag pTag) {
        if (this.posRef != null)
            pTag.put(saveTag, NbtUtils.writeBlockPos(this.posRef));
    }

    public void fromNBT(CompoundTag pTag) {
        if (pTag.contains(saveTag))
            posRef = NbtUtils.readBlockPos(pTag.getCompound(saveTag));
    }
}
