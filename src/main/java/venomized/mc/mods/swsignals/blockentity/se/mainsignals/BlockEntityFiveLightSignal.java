package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;

public class BlockEntityFiveLightSignal extends BlockEntitySignal {
	public BlockEntityFiveLightSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
		super(t, pPos, pBlockState, 5);
	}
}
