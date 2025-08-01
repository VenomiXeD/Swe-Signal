package venomized.mc.mods.swsignals.block.se.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.Sw45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityCrossingGate;

public class BlockCrossingGate extends Sw45DegreeBlock implements EntityBlock {
	public BlockCrossingGate(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return SwBlockEntities.BE_CROSSING_GATE.create(blockPos, blockState);
	}

}
