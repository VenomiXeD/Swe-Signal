package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityThreeLightSignalBlock extends BlockEntitySignalBlock{
	public static final String NAME = "be_3l";

	public BlockEntityThreeLightSignalBlock(BlockPos pPos, BlockState pBlockState) {
		super(pPos, pBlockState, 3);
	}
}
