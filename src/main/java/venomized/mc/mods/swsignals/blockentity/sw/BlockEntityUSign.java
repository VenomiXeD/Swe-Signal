package venomized.mc.mods.swsignals.blockentity.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntityBase;

public class BlockEntityUSign extends SwBlockEntityBase {
	/**
	 * @param pPos
	 * @param pBlockState
	 */
	public BlockEntityUSign(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_U_SIGN.get(), pPos, pBlockState);
	}
}
