package venomized.mods.extendedsignals.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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

    private BlockPos posRef;

    public BlockEntityReference(Class<T> compatibleType) {
        this.compatibleType = compatibleType;
    }

    public Optional<T> getReference(BlockGetter world) {
        if (this.posRef == null) {
            return Optional.empty();
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

    public CompoundTag toNBT(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();

        if (this.posRef != null)
            tag.put("reference_position", NbtUtils.writeBlockPos(this.posRef));

        return tag;
    }

    public void fromNBT(CompoundTag pTag, HolderLookup.Provider registries) {
        NbtUtils.readBlockPos(pTag, "reference_position").ifPresent(pos -> posRef = pos);
    }
}
