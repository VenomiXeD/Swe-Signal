package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityFourLightSignalBlock extends BlockEntitySignalBlock {
	public static final String NAME = "be_4l_modern";

	public BlockEntityFourLightSignalBlock(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.get(), pPos, pBlockState, 4);
	}
}
