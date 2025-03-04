package venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingObjectBase;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockEntityRailroadCrossingSignal extends BlockEntityRailroadCrossingObjectBase implements ISignalTunerBindable {
	public BlockEntityRailroadCrossingSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_RAILROAD_CROSSING_SIGNAL.get(), pPos, pBlockState);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, Object blockEntity) {
		if (isRailroadCrossingControllerPowered()) {
			if (level.getGameTime() % 10 == 0) {
				level.playLocalSound(
						pos,
						AllSounds.SE_TEST.get(),
						SoundSource.BLOCKS,
						1, 1, false
				);
			}

			if (level.getGameTime() % 11 == 0) {
				level.playLocalSound(
						pos,
						AllSounds.SE_TEST.get(),
						SoundSource.BLOCKS,
						1, 1.01f, false
				);
			}
		}
	}
}
