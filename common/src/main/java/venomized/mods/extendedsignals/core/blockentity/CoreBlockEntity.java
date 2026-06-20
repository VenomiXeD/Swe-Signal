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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public abstract class CoreBlockEntity extends BlockEntity implements IConfigurableModelBlockEntity {
    private final float[] orientation;
    private final double[] locOffsets;
    private final double[] gblOffsets;

    public CoreBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);

        orientation = new float[3];
        locOffsets = new double[3];
        gblOffsets = new double[3];
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
        setXOrientation(pTag.getFloat("x_orientation"));

        // Migration
        if (pTag.contains("orientation_rotation"))
            setYOrientation(pTag.getFloat("orientation_rotation"));
        else
            setYOrientation(pTag.getFloat("y_orientation"));

        setZOrientation(pTag.getFloat("z_orientation"));


        setXLocOffset(pTag.getDouble("x_loc_offset"));
        setYLocOffset(pTag.getDouble("y_loc_offset"));
        setZLocOffset(pTag.getDouble("z_loc_offset"));

        setXGblOffset(pTag.getDouble("x_gbl_offset"));
        setYGblOffset(pTag.getDouble("y_gbl_offset"));
        setZGblOffset(pTag.getDouble("z_gbl_offset"));
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
        pTag.putDouble("x_orientation", this.getXOrientation());
        pTag.putDouble("y_orientation", this.getYOrientation());
        pTag.putDouble("z_orientation", this.getZOrientation());
        pTag.putDouble("x_loc_offset", getXLocOffset());
        pTag.putDouble("y_loc_offset", getYLocOffset());
        pTag.putDouble("z_loc_offset", getZLocOffset());

        pTag.putDouble("x_gbl_offset", getXGblOffset());
        pTag.putDouble("y_gbl_offset", getYGblOffset());
        pTag.putDouble("z_gbl_offset", getZGblOffset());
    }

    /**
     * @return
     */
    @Override
    public float getXOrientation() {
        return orientation[0];
    }

    /**
     * @return
     */
    @Override
    public float getYOrientation() {
        return orientation[1];
    }

    /**
     * @return
     */
    @Override
    public float getZOrientation() {
        return orientation[2];
    }

    /**
     * @param rotInDeg
     */
    @Override
    public void setXOrientation(float rotInDeg) {
        orientation[0] = rotInDeg;
        sync();
    }

    /**
     * @param rotInDeg
     */
    @Override
    public void setYOrientation(float rotInDeg) {
        orientation[1] = rotInDeg;
        sync();
    }

    /**
     * @param rotInDeg
     */
    @Override
    public void setZOrientation(float rotInDeg) {
        orientation[2] = rotInDeg;
        sync();
    }

    /**
     * @param orientationInDeg
     */
    @Override
    public void setOrientation(Vec3 orientationInDeg) {
        orientation[0] = (float) orientationInDeg.x();
        orientation[1] = (float) orientationInDeg.y();
        orientation[2] = (float) orientationInDeg.z();
    }

    /**
     * @return
     */
    @Override
    public double getXLocOffset() {
        return locOffsets[0];
    }

    /**
     * @return
     */
    @Override
    public double getYLocOffset() {
        return locOffsets[1];
    }

    /**
     * @return
     */
    @Override
    public double getZLocOffset() {
        return locOffsets[2];
    }

    /**
     * @param offset
     * @return
     */
    @Override
    public void setXLocOffset(double offset) {
        locOffsets[0] = offset;
        sync();
    }

    /**
     * @param offset
     * @return
     */
    @Override
    public void setYLocOffset(double offset) {
        locOffsets[1] = offset;
        sync();
    }

    /**
     * @param offset
     * @return
     */
    @Override
    public void setZLocOffset(double offset) {
        locOffsets[2] = offset;
        sync();
    }

    /**
     * @param offset
     */
    @Override
    public void setLocOffset(Vec3 offset) {
        locOffsets[0] = offset.x();
        locOffsets[1] = offset.y();
        locOffsets[2] = offset.z();
        sync();
    }

    /**
     * @return
     */
    @Override
    public double getXGblOffset() {
        return this.gblOffsets[0];
    }

    /**
     * @return
     */
    @Override
    public double getYGblOffset() {
        return this.gblOffsets[1];
    }

    /**
     * @return
     */
    @Override
    public double getZGblOffset() {
        return this.gblOffsets[2];
    }

    /**
     * @param offset
     */
    @Override
    public void setXGblOffset(double offset) {
        this.gblOffsets[0] = offset;
        sync();
    }

    /**
     * @param offset
     */
    @Override
    public void setYGblOffset(double offset) {
        this.gblOffsets[1] = offset;
        sync();
    }

    /**
     * @param offset
     */
    @Override
    public void setZGblOffset(double offset) {
        this.gblOffsets[2] = offset;
        sync();
    }

    /**
     * @param offset
     */
    @Override
    public void setGblOffset(Vec3 offset) {
        gblOffsets[0] = offset.x();
        gblOffsets[1] = offset.y();
        gblOffsets[2] = offset.z();
        sync();
    }

    /**
     * @return
     */
    @Override
    public @Nullable EnumSet<?> variations() {
        return null;
    }
}
