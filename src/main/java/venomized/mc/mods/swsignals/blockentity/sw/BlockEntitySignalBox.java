package venomized.mc.mods.swsignals.blockentity.sw;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityAbstractSignalBox;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntitySignalBox extends BlockEntityAbstractSignalBox {
	public static final String NAME = "be_sw_signal_box";

	private SwedishSignalAspect cachedAspect;

	public BlockEntitySignalBox(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_SW_SIGNAL_BOX.get(), pPos, pBlockState);
	}

	public SwedishSignalAspect getCurrentAspect() {
		SwedishSignalAspect result;

		SignalBlockEntity.SignalState state = this.getCreateSignalState();

		if (state == null) {
			return cachedAspect;
		}

		result = switch (state) {
			case GREEN -> SwedishSignalAspect.PROCEED_80;
			case YELLOW -> SwedishSignalAspect.PROCEED_40_CAUTION;
			case RED, INVALID -> SwedishSignalAspect.STOP;
		};
		this.cachedAspect = result;
		if (result == SwedishSignalAspect.STOP) {
			return result;
		}

		// Determine the aspect from the next coming signal
		if (this.getSignalBoxBlockEntity() instanceof BlockEntitySignalBox sb) {
			result = sb.getCurrentAspect();
			if (result != null) {
				result = switch (result) {
					case STOP -> SwedishSignalAspect.PROCEED_40_CAUTION;
					case PROCEED_40_CAUTION -> SwedishSignalAspect.PROCEED_80;
					case PROCEED_80 -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_80;
					case PROCEED_80_EXPECT_PROCEED_80 -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;
					default -> result;
				};
			}
		}

		this.cachedAspect = result;
		return result;
	}
}
