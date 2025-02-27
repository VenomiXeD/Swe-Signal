package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.SwSignal;

public class BlockEntityThreeLightSignalBlock extends BlockEntitySignalBlock {
	public static final String NAME = "be_3l_modern";

	public BlockEntityThreeLightSignalBlock(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_THREE_LIGHT_SIGNAL.get(), pPos, pBlockState, 3);
	}
}
