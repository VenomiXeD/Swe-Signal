package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityTwoLightSignalBlock extends BlockEntitySignalBlock{
	public static final String NAME = "be_2l_modern";

	public BlockEntityTwoLightSignalBlock(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_TWO_LIGHT_SIGNAL.get(), pPos, pBlockState, 2);
	}
}
