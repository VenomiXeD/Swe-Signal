package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.BlockRailroadCrossingObjectBase;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityRailroadCrossingSignal;

public class BlockRailroadCrossingSignal extends BlockRailroadCrossingObjectBase {
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntityRailroadCrossingSignal(pPos, pState);
	}
}
