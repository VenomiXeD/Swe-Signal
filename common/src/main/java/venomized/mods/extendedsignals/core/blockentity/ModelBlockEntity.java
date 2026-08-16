package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ModelBlockEntity extends CoreBlockEntity implements IConfigurableModelBlockEntity {
    private final float[] orientation;
    private final double[] locOffsets;
    private final double[] gblOffsets;

    private final VariantData variantData;

    public ModelBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);

        orientation = new float[3];
        locOffsets = new double[3];
        gblOffsets = new double[3];

        variantData = constructVariantData();
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setXOrientation(tag.getFloat("x_orientation"));
        setYOrientation(tag.getFloat("y_orientation"));
        setZOrientation(tag.getFloat("z_orientation"));


        setXLocOffset(tag.getDouble("x_loc_offset"));
        setYLocOffset(tag.getDouble("y_loc_offset"));
        setZLocOffset(tag.getDouble("z_loc_offset"));

        setXGblOffset(tag.getDouble("x_gbl_offset"));
        setYGblOffset(tag.getDouble("y_gbl_offset"));
        setZGblOffset(tag.getDouble("z_gbl_offset"));

        variantData.read(tag.getCompound("variant_data"));
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("x_orientation", this.getXOrientation());
        tag.putDouble("y_orientation", this.getYOrientation());
        tag.putDouble("z_orientation", this.getZOrientation());
        tag.putDouble("x_loc_offset", getXLocOffset());
        tag.putDouble("y_loc_offset", getYLocOffset());
        tag.putDouble("z_loc_offset", getZLocOffset());

        tag.putDouble("x_gbl_offset", getXGblOffset());
        tag.putDouble("y_gbl_offset", getYGblOffset());
        tag.putDouble("z_gbl_offset", getZGblOffset());

        tag.put("variant_data", variantData.toNBT());
    }

    /**
     * @return
     */
    @Override
    public float getXOrientation() {
        return orientation[0];
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
     * @return
     */
    @Override
    public float getYOrientation() {
        return orientation[1];
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
     * @param offset
     * @return
     */
    @Override
    public void setXLocOffset(double offset) {
        locOffsets[0] = offset;
        sync();
    }

    /**
     * @return
     */
    @Override
    public double getYLocOffset() {
        return locOffsets[1];
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
     * @param offset
     */
    @Override
    public void setXGblOffset(double offset) {
        this.gblOffsets[0] = offset;
        sync();
    }

    /**
     * @return
     */
    @Override
    public double getYGblOffset() {
        return this.gblOffsets[1];
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
    public VariantData variantData() {
        return variantData;
    }


    protected VariantData constructVariantData() {
        VariantData variants = new VariantData();
        variants.addVariantOption(new VariantData.VariantOption("default", Component.translatable("screens.extended_signals.modelconfig.variants.default"), () -> null));

        return variants;
    }
}
