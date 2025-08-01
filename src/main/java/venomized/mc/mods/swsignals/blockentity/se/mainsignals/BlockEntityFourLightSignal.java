package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;

public class BlockEntityFourLightSignal extends BlockEntitySignal {
	public static final String NAME = "be_4l_modern";

	public BlockEntityFourLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
		super(t, pPos, pBlockState, 4);
	}
}
