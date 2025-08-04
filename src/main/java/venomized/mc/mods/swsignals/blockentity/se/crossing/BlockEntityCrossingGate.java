package venomized.mc.mods.swsignals.blockentity.se.crossing;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingController;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingObjectBase;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntityBase;
import venomized.mc.mods.swsignals.util.BEPosRef;

import java.util.Optional;

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
