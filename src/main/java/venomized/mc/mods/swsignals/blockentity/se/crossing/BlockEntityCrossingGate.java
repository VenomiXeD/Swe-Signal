package venomized.mc.mods.swsignals.blockentity.se.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntityBase;

public class BlockEntityCrossingGate extends SwBlockEntityBase {
	private float rotationTick;

	public BlockEntityCrossingGate(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

	public float rotation() {
		return rotationTick;
	}

	public void rotationDelta(float delta) {
		rotationTick += delta;
	}
}
