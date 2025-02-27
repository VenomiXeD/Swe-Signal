package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityFiveLightSignalBlock extends BlockEntitySignalBlock {
	public static final String NAME = "be_5l_modern";

	public BlockEntityFiveLightSignalBlock(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_FIVE_LIGHT_SIGNAL.get(), pPos, pBlockState, 5);
	}
}
