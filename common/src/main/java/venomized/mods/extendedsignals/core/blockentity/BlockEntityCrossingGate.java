package venomized.mods.extendedsignals.core.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.util.TrackedValue;

public abstract class BlockEntityCrossingGate extends BlockEntityCrossingObject {
    @Getter(AccessLevel.PROTECTED)
    private long gateActivationTick = -1;
    private final TrackedValue<Boolean> gateDown = new TrackedValue<>(false, this::onGateActivationChanged);

    private void onGateActivationChanged(boolean oldValue, boolean newValue) {
        gateActivationTick = level.getGameTime();
    }

    public BlockEntityCrossingGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public float getProgressPercent(float partialTick) {
        float progress = 0;
        float t = 0;
        if (gateActivationTick + gateArmMovementDelayTicks() < level.getGameTime() && gateDown.value()) {
            t = (level.getGameTime() - gateActivationTick - gateArmMovementDelayTicks()) + partialTick;
        } else if (!gateDown.value()) {
            t = (level.getGameTime() - gateActivationTick) + partialTick;
        }

        progress = Mth.clamp(t / gateArmMovementTimeTicks(), 0f, 1f);
        if (!gateDown.value())
            progress = 1f - progress;

        return progress;
    }

    public float getArmRotation(float partialTick) {
        return (1f - getProgressPercent(partialTick)) * gateArmUpRotationAngle();
    }

    public void setGateDown(boolean gateDown) {
        this.gateDown.change(gateDown);
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
    public abstract float gateArmMovementTimeTicks();

    public float gateArmUpRotationAngle() {
        return 90f;
    }

    public long gateArmMovementDelayTicks() {
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract PartialModel gateArmModel();

    @OnlyIn(Dist.CLIENT)
    public abstract Vector3f gateArmPivotPoint();
}
