package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;

public class BlockEntityFiveLightSignal extends BlockEntitySignal {
	public static final String NAME = "be_5l_modern";

	public BlockEntityFiveLightSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_FIVE_LIGHT_SIGNAL.get(), pPos, pBlockState, 5);
	}
}
