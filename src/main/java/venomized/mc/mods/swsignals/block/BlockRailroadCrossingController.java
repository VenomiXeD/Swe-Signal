package venomized.mc.mods.swsignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingController;

public class BlockRailroadCrossingController extends SwAbstractBlock implements EntityBlock {
	/**
	 * @param pProperties
	 */
	public BlockRailroadCrossingController() {
		super(Properties.copy(Blocks.IRON_BLOCK));
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityRailroadCrossingController(pPos, pState);
	}

	@Override
	public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
		if (pLevel.getGameTime() % 10 == 0) {
			pLevel.playLocalSound(pPos, AllSounds.SE_TEST.get(), SoundSource.BLOCKS, 1, 1.015f, true);
		}

		if (pLevel.getGameTime() % 11 == 0) {
			pLevel.playLocalSound(pPos, AllSounds.SE_TEST.get(), SoundSource.BLOCKS, 1, 1, true);
		}
	}
}
