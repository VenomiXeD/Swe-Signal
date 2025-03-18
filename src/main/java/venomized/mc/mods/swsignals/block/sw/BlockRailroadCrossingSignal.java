package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.BlockRailroadCrossingObjectBase;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityRailroadCrossingSignal;

public class BlockRailroadCrossingSignal extends BlockRailroadCrossingObjectBase {
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityRailroadCrossingSignal(pPos, pState);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return pBlockEntityType == SwBlockEntities.BE_RAILROAD_CROSSING_SIGNAL.get() ? (level, pos, state, blockEntity) -> {
			((BlockEntityRailroadCrossingSignal) blockEntity).tick(level, pos, state, blockEntity);
		} : null;
	}
}
