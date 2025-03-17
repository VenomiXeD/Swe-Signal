package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityMainDwarfSignal;

public class BlockModernMainDwarfSignal extends BlockModernDwarfSignal {

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityMainDwarfSignal(pPos, pState);
	}
}
