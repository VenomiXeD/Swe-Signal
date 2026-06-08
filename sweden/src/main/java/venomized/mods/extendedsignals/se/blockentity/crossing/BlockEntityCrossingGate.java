package venomized.mods.extendedsignals.se.blockentity.crossing;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;
import venomized.mods.extendedsignals.core.util.MathHelp;
import venomized.mods.extendedsignals.se.ExtendedSignalsSwedenSounds;
import venomized.mods.extendedsignals.se.client.SwedenModels;

public class BlockEntityCrossingGate extends venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate {
    public BlockEntityCrossingGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    private int tick;

    /**
     * @param partialTick
     * @return
     */
    @Override
    public float getProgressPercent(float partialTick) {
        return MathHelp.easeInOutBack(super.getProgressPercent(partialTick), 1.05f, 1.1f);
    }

    /**
     * @return
     */
    @Override
    public float getArmMovementTimeTicks() {
        return 20f * 15f;
    }

    /**
     * @return
     */
    @Override
    public PartialModel getCrossingArmModel() {
        return SwedenModels.ARM_4;
    }

    /**
     * @return
     */
    @Override
    public double getArmRotationHeightPoint() {
        return 17d / 16d;
    }

    public static <S extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, S s) {
        BlockEntityCrossingGate be = (BlockEntityCrossingGate) s;
        be.tick++;

        if (be.isActive()) {
            if (be.tick % 10 == 0) {
                level.playLocalSound(
                        blockPos, ExtendedSignalsSwedenSounds.SE_CROSSING_BELL.get(), SoundSource.BLOCKS, 1, 1f, true
                );
            } else if (be.tick % 11 == 0) {
                level.playLocalSound(
                        blockPos, ExtendedSignalsSwedenSounds.SE_CROSSING_BELL.get(), SoundSource.BLOCKS, 1, 1.01f, true
                );
            }
        }
    }


    // public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, BlockEntityCrossingGate t) {
    //     t.ARM_MOVEMENT_TICKS = Mth.clamp(t.ARM_MOVEMENT_TICKS + (t.isRailroadCrossingControllerPowered() ? 1 : -1), 0, MAX_ARM_MOVEMENT_TICKS);

}
