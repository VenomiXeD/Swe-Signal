package venomized.mc.mods.swsignals.blockentity.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockEntityFourLightSignal extends BlockEntitySignalBlock {
	public static final String NAME = "be_4l_modern";

	public BlockEntityFourLightSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.get(), pPos, pBlockState, 4);
	}
}
