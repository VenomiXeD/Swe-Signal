package venomized.mc.mods.swsignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.BlockEntitySignalBlock;
import venomized.mc.mods.swsignals.blockentity.BlockEntityThreeLightSignalBlock;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockThreeLightSignal extends BlockAbstractSignal implements EntityBlock {
	public static final String BLOCK_NAME = "signal_3l";

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityThreeLightSignalBlock(pPos, pState);
	}

	/**
	 * @param pLevel
	 * @param pState
	 * @param pBlockEntityType
	 * @param <T>
	 * @return
	 */
	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return BlockEntitySignalBlock::worldTick;
	}
}
