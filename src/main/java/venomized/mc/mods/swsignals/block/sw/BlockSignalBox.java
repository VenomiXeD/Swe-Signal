package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.SwAbstractBlock;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntitySignalBox;

public class BlockSignalBox extends SwAbstractBlock implements EntityBlock {
	public static final String BLOCK_NAME = "sw_signal_box";

	public BlockSignalBox() {
		super(Properties.copy(Blocks.IRON_BLOCK));
	}

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntitySignalBox(pPos, pState);
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
		return EntityBlock.super.getTicker(pLevel, pState, pBlockEntityType);
	}


}
