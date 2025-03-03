package venomized.mc.mods.swsignals.blockentity.sw;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityEndpointSignal extends BlockEntitySignal {
	public static final String NAME = "be_sw_1l_endpoint_signal";

	public BlockEntityEndpointSignal(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_ENDPOINT_SIGNAL.get(), pPos, pBlockState, 1);
	}

	@Override
	public void stepSignalLighting(float partialTick, SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
		float r = 0f;
		if (doInvalidBlinking || aspect == null) {
			this.lightLevels[0] = this.blink() ? 1 : 0;
			return;
		}
		switch (aspect) {
			case STOP:
				this.lightLevels[0] = Math.min(this.lightLevels[0] + partialTick / 20, 1);
				break;
			default:
				this.lightLevels[0] = Math.max(this.lightLevels[0] - partialTick / 20, 0);
				break;
		}
	}
}
