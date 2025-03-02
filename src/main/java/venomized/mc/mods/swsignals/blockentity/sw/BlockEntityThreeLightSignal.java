package venomized.mc.mods.swsignals.blockentity.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityThreeLightSignal extends BlockEntitySignalBlock {
	public static final String NAME = "be_3l_modern";

	public BlockEntityThreeLightSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_THREE_LIGHT_SIGNAL.get(), pPos, pBlockState, 3);
	}

	@Override
	public SwedishSignalAspect getCurrentDisplayingAspect() {
		SwedishSignalAspect displayingAspect = super.getCurrentDisplayingAspect();
		if (displayingAspect == SwedishSignalAspect.PROCEED_80_EXPECT_STOP) {
			return SwedishSignalAspect.PROCEED_40_CAUTION;
		}
		return displayingAspect;
	}
}
