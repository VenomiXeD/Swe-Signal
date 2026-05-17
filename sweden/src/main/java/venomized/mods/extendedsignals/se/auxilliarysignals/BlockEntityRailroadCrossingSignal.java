package venomized.mods.extendedsignals.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.blockentity.BlockEntityRailroadCrossingObject;
import venomized.mods.extendedsignals.blockentity.ISignalTunerBindable;
import venomized.mods.extendedsignals.se.ExtendedSignalsSwedenSounds;

public class BlockEntityRailroadCrossingSignal extends BlockEntityRailroadCrossingObject implements ISignalTunerBindable {
    public BlockEntityRailroadCrossingSignal(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }


    public void tick(Level level, BlockPos pos, BlockState state, Object blockEntity) {
        if (level.isClientSide()) return;
        if (isRailroadCrossingControllerPowered()) {
            if (level.getGameTime() % 10 == 0) {
                level.playSound(
                        null,
                        pos,
                        ExtendedSignalsSwedenSounds.SE_CROSSING_BELL.get(),
                        SoundSource.BLOCKS,
                        1, 1
                );
            }
            if (level.getGameTime() % 11 == 0) {
                level.playSound(
                        null,
                        pos,
                        ExtendedSignalsSwedenSounds.SE_CROSSING_BELL.get(),
                        SoundSource.BLOCKS,
                        1, 1.01f
                );
            }
        }
    }
}
