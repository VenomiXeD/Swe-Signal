package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.sw.mainsignals.BlockEntityFiveLightSignal;

public class BlockModernFiveLightSignal extends BlockAbstractSignal {
	public static String BLOCK_NAME = "signal_5l_modern";

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityFiveLightSignal(pPos, pState);
	}
}
