package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;

public class BlockEntityFourLightSignal extends BlockEntitySignal {
	public static final String NAME = "be_4l_modern";

	public BlockEntityFourLightSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.get(), pPos, pBlockState, 4);
	}
}
