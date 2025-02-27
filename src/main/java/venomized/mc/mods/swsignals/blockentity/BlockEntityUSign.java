package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SwBlockEntityBasic extends BlockEntity {
	/**
	 * @param pPos
	 * @param pBlockState
	 */
	public SwBlockEntityBasic(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_BASIC.get(), pPos, pBlockState);
	}
}
