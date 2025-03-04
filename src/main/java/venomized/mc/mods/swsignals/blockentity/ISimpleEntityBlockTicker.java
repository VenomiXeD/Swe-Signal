package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface ISimpleEntityBlockTicker<T extends BlockEntity, I extends ITickingEntity> extends EntityBlock {
	BlockEntityType<T> getBlockEntityType();

	@Override
	@Nullable
	default <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		BlockEntityTicker<T> result = null;

		if (getBlockEntityType() == pBlockEntityType) {
			result = (level, pos, state, blockEntity) -> {
				((I) blockEntity).tick(level, pos, state, blockEntity);
			};
		}
		return result;
	}

	@Override
	@Nullable
	default BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return getBlockEntityType().create(pPos, pState);
	}
}
