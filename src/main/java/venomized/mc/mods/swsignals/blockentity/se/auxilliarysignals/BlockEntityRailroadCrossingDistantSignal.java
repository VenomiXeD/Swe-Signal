package venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingObjectBase;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockEntityRailroadCrossingDistantSignal extends BlockEntityRailroadCrossingObjectBase {
	private int tick;

	public float lightLevel = 0;

	public BlockEntityRailroadCrossingDistantSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_RAILROAD_CROSSING_DISTANT_SIGNAL.get(), pPos, pBlockState);
	}

	public boolean blink() {
		return tick > 10;
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, Object blockEntity) {
		this.tick = (this.tick + 1) % 20;
	}
}
