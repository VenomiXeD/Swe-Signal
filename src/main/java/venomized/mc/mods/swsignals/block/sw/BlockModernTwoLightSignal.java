package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntityTwoLightSignal;

public class BlockModernTwoLightSignal extends BlockAbstractSignal {
	public static final String BLOCK_NAME = "signal_2l_modern";

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityTwoLightSignal(pPos, pState);
	}
}
