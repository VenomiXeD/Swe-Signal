package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityUSign extends BlockEntity {
	/**
	 * @param pPos
	 * @param pBlockState
	 */
	public BlockEntityUSign(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_U_SIGN.get(), pPos, pBlockState);
	}
}
