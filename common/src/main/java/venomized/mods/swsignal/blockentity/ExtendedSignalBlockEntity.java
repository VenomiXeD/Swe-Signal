package venomized.mods.swsignal.blockentity;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class ExtendedSignalBlockEntity extends BlockEntity {
    private static final String ORIENTATION_INDEX_NBT_KEY = "orientation_index";

    @Getter @Setter
    private int orientationIndex;

    public float orientationIndexInDegrees() { return getOrientationIndex() * 22.5F; }

    public ExtendedSignalBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void updateSelf() {
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
        this.setChanged();
    }

    /**
     * @param pTag
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.setOrientationIndex(pTag.getInt(ORIENTATION_INDEX_NBT_KEY));
    }

    /**
     * @return
     */
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * @return
     */
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    /**
     * @param tag The {@link CompoundTag} sent from {@link BlockEntity#getUpdateTag()}
     */
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.setOrientationIndex(tag.getInt(ORIENTATION_INDEX_NBT_KEY));
    }



    /**
     * @param pTag
     */
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt(ORIENTATION_INDEX_NBT_KEY, this.getOrientationIndex());
    }
}
