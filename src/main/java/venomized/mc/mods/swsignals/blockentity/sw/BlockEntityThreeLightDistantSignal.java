package venomized.mc.mods.swsignals.blockentity.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityThreeLightDistantSignal extends BlockEntitySignalBlock {
	public BlockEntityThreeLightDistantSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_THREE_LIGHT_DISTANT_SIGNAL.get(), pPos, pBlockState, 5); // Trick it - so it can compute distant signal states
	}

	@Override
	public SwedishSignalAspect getCurrentDisplayingAspect() {
		SwedishSignalAspect aspect = super.getCurrentDisplayingAspect();
		if (aspect == null) {
			return null;
		}
		// Remap to distant signal aspects
		return switch (aspect) {
			case STOP -> SwedishSignalAspect.PROCEED_80_EXPECT_STOP;
			case PROCEED_40_SHORT, PROCEED_40_CAUTION -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;
			default -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_80;
		};
	}
}
