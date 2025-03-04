package venomized.mc.mods.swsignals.blockentity.sw.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntitySignal;

public class BlockEntityTwoLightSignal extends BlockEntitySignal {
	public static final String NAME = "be_2l_modern";

	public BlockEntityTwoLightSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_TWO_LIGHT_SIGNAL.get(), pPos, pBlockState, 2);
	}
}
