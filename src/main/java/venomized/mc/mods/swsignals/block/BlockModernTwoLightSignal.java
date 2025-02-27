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
import venomized.mc.mods.swsignals.blockentity.BlockEntityTwoLightSignalBlock;

public class BlockModernTwoLightSignal extends BlockAbstractSignal {
	public static final String BLOCK_NAME = "signal_2l_modern";

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityTwoLightSignalBlock(pPos, pState);
	}
}
