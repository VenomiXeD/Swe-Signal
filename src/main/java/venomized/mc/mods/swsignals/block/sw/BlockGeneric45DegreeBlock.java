package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.SwAbstract45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityUSign;

public class BlockGeneric45DegreeBlock extends SwAbstract45DegreeBlock implements EntityBlock {
	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityUSign(pPos, pState);
	}


	public BlockGeneric45DegreeBlock() {
		super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noCollission());
	}
}
