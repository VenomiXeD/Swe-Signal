package venomized.mc.mods.swsignals.blockentity.se;

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
			return SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING;
		}

		result = switch (state) {
			case GREEN -> SwedishSignalAspect.PROCEED_80;
			case YELLOW -> SwedishSignalAspect.PROCEED_40_CAUTION;
			case RED -> SwedishSignalAspect.STOP;
			default -> SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING;
		};

		if (result == SwedishSignalAspect.STOP || result == SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING) {
			return result;
		}

		// Determine the aspect from the next coming signal
		if (this.getSignalBoxBlockEntity() instanceof BlockEntitySignalBox signalBox) {
			if (signalBox == this) {
				return SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING;
			}
			if (signalBox != null) {
				SwedishSignalAspect nextSignal = signalBox.getCurrentAspect();

				if (nextSignal != null) {
					result = switch (nextSignal) {
						case STOP, SIGNAL_FAULT_INCORRECT_WIRING -> SwedishSignalAspect.PROCEED_40_CAUTION;
						case PROCEED_40_SHORT, PROCEED_40_CAUTION -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;
						case PROCEED_80_EXPECT_STOP, PROCEED_80, PROCEED_80_EXPECT_PROCEED_80 -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_80;
						case PROCEED_80_EXPECT_PROCEED_40 ->  SwedishSignalAspect.PROCEED_80;
					};
				}
			}

		}
		this.cachedAspect = result;
		return result;
	}
}
