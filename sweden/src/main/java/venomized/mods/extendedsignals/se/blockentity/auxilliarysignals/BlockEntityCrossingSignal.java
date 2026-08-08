package venomized.mods.extendedsignals.se.blockentity.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingObject;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;

public class BlockEntityCrossingSignal extends BlockEntityCrossingObject implements ISignalTunerToolable {
    public BlockEntityCrossingSignal(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }


    // public void tick(Level level, BlockPos pos, BlockState state, Object blockEntity) {
    //     if (level.isClientSide()) return;
    //     if (isRailroadCrossingControllerPowered()) {
    //         if (level.getGameTime() % 10 == 0) {
    //             level.playSound(
    //                     null,
    //                     pos,
    //                     ExtendedSignalsSwedenSounds.SE_CROSSING_BELL.get(),
    //                     SoundSource.BLOCKS,
    //                     1, 1
    //             );
    //         }
    //         if (level.getGameTime() % 11 == 0) {
    //             level.playSound(
    //                     null,
    //                     pos,
    //                     ExtendedSignalsSwedenSounds.SE_CROSSING_BELL.get(),
    //                     SoundSource.BLOCKS,
    //                     1, 1.01f
    //             );
    //         }
    //     }
    // }
}
