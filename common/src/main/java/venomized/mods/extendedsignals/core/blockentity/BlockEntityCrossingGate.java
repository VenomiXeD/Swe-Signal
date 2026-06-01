package venomized.mods.extendedsignals.core.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BlockEntityCrossingGate extends BlockEntityCrossingObject {
    public BlockEntityCrossingGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    private long gateMovementStart = -1;
    private boolean gateIsDown = false;

    public float getProgressPercent(float partialTick) {
        float t = (level.getGameTime() - gateMovementStart) + partialTick;
        float progress = Mth.clamp(t / getArmMovementTimeTicks(), 0f, 1f);

        if (!gateIsDown)
            progress = 1f - progress;

        return progress;
    }

    public float getArmRotation(float partialTick) {
        return (1f - getProgressPercent(partialTick)) * 90f;
    }

    public void setGateDown(boolean gateDown) {
        if (gateDown == gateIsDown)
            return;

        gateIsDown = gateDown;
        gateMovementStart = level.getGameTime();
    }

    // /**
    //  * @param tag The {@link CompoundTag} sent from {@link BlockEntity#getUpdateTag()}
    //  */
    // @Override
    // public void handleUpdateTag(CompoundTag tag) {
    //     super.handleUpdateTag(tag);
    //     // gateIsDown = tag.getBoolean("down");
    //     // gateMovementStart = tag.getLong("start");
    // }

    /**
     * @return
     */
    //@Override
    //public CompoundTag getUpdateTag() {
    //    CompoundTag tag = super.getUpdateTag();
    //    // tag.putBoolean("down",gateIsDown);
    //    // tag.putLong("start",gateMovementStart);

    //    return tag;
    //}
    public abstract float getArmMovementTimeTicks();

    @OnlyIn(Dist.CLIENT)
    public abstract PartialModel getCrossingArmModel();

    @OnlyIn(Dist.CLIENT)
    public abstract double getArmRotationHeightPoint();
}
