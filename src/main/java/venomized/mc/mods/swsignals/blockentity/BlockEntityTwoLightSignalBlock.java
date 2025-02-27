package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityTwoLightSignalBlock extends BlockEntitySignalBlock{
	public static final String NAME = "be_2l";

	public BlockEntityTwoLightSignalBlock(BlockPos pPos, BlockState pBlockState) {
		super(pPos, pBlockState, 2);
	}
}
