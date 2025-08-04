package venomized.mc.mods.swsignals.blockentity.se.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingObjectBase;

public class BlockEntityCrossingGate extends BlockEntityRailroadCrossingObjectBase {
	private float progressPercent;

	public BlockEntityCrossingGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

	public float progress() {
		return progressPercent;
	}

	public void progressDelta(float delta) {
		progressPercent = Mth.clamp(progressPercent + delta * (isRailroadCrossingControllerPowered() ? -1 : 1), 0f, 1f);
	}
}
