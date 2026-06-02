package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class CoreBlockEntity extends BlockEntity implements IOrientedBlockEntity, ITranslatableBlockEntity {
    private float yOrientation;
    private final double[] offsets;

    public CoreBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);

        yOrientation = 0;
        offsets = new double[3];
    }

    public void sync() {
        this.setChanged();
        if (this.getLevel() == null)
            return;
        this.getLevel()
                .sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
    }

    public void onBlockDestroyed(Player player) {
    }

    /**
     * @param pTag
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.setYOrientation(pTag.getFloat(TAG_ORIENTATION_INDEX_NAME));
        this.setXOffset(pTag.getDouble("x_offset"));
        this.setYOffset(pTag.getDouble("y_offset"));
        this.setZOffset(pTag.getDouble("z_offset"));
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
        this.load(tag);

    }


    /**
     * @param pTag
     */
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putDouble(TAG_ORIENTATION_INDEX_NAME, this.getYOrientation());
        pTag.putDouble("x_offset", getXOffset());
        pTag.putDouble("y_offset", getYOffset());
        pTag.putDouble("z_offset", getZOffset());
    }

    /**
     * @return
     */
    @Override
    public float getYOrientation() {
        return yOrientation;
    }

    /**
     * @param pYOrientation
     */
    @Override
    public void setYOrientation(float pYOrientation) {
        yOrientation = pYOrientation;
        sync();
    }

    /**
     * @return
     */
    @Override
    public double getXOffset() {
        return offsets[0];
    }

    /**
     * @return
     */
    @Override
    public double getYOffset() {
        return offsets[1];
    }

    /**
     * @return
     */
    @Override
    public double getZOffset() {
        return offsets[2];
    }

    /**
     * @param offset
     * @return
     */
    @Override
    public void setXOffset(double offset) {
        offsets[0] = offset;
        sync();
    }

    /**
     * @param offset
     * @return
     */
    @Override
    public void setYOffset(double offset) {
        offsets[1] = offset;
        sync();
    }

    /**
     * @param offset
     * @return
     */
    @Override
    public void setZOffset(double offset) {
        offsets[2] = offset;
        sync();
    }
}
