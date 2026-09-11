package venomized.mods.extendedsignals.nl.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.nl.NetherlandSounds;
import venomized.mods.extendedsignals.nl.client.NetherlandModels;

public class BlockEntityGate extends BlockEntityCrossingGate {
    public BlockEntityGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public float gateArmMovementTimeTicks() {
        return 20f * 8f;
    }

    /**
     * @return
     */
    @Override
    public long gateArmMovementDelayTicks() {
        return 20 * 5;
    }

    /**
     * @return
     */
    @Override
    public float gateArmUpRotationAngle() {
        return 80f;
    }

    /**
     * @return
     */
    @Override
    public PartialModel gateArmModel() {
        return NetherlandModels.GATE_ARM;
    }

    /**
     * @return
     */
    @Override
    public Vector3f gateArmPivotPoint() {
        return new Vector3f(0f, 20f / 16f - 0.5f, 1.4f / 16f);
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        BlockEntityGate be = (BlockEntityGate) t;
        be.setGateDown(be.crossingControllerActive());
        if (!be.crossingControllerActive() || level.getGameTime() % 4 != 0)
            return;

        level.playSound(
                null, be.getBlockPos(), NetherlandSounds.BELL_VIALIS_EBA.get(), SoundSource.BLOCKS, Mth.clampedMap(
                        level.getGameTime(), be.getGateActivationTick(), be.getGateActivationTick() + 80, 0f, 1f
                ), 1f
        );
    }
}
