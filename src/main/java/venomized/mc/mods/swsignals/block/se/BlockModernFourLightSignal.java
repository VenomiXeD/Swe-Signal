package venomized.mc.mods.swsignals.block.se;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntityFourLightSignal;

public class BlockModernFourLightSignal extends BlockSignal {
	public BlockModernFourLightSignal(Properties properties) {
		super(properties);
	}

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.create(pPos, pState);
	}
}
