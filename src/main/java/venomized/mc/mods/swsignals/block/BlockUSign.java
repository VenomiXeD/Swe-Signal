package venomized.mc.mods.swsignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.BlockEntityUSign;

public class BlockUSign extends SwAbstract45DegreeBlock implements EntityBlock {
	public static final String BLOCK_NAME = "sign_u";


	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityUSign(pPos, pState);
	}


	public BlockUSign() {
		super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noCollission());
	}
}
