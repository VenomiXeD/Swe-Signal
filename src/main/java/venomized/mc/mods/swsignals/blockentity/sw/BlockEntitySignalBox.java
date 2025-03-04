package venomized.mc.mods.swsignals.blockentity.sw;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityAbstractSignalBox;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntitySignalBox extends BlockEntityAbstractSignalBox {
	public static final String NAME = "be_sw_signal_box";

	public BlockEntitySignalBox(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_SW_SIGNAL_BOX.get(), pPos, pBlockState);
	}

	public SwedishSignalAspect getCurrentAspect() {
		SwedishSignalAspect result;
		SignalBlockEntity.SignalState state = this.getCreateSignalState();

		if (state == null) {
			return SwedishSignalAspect.FAULTY_RAIL_SIGNALS;
		}

		switch (state) {
			case GREEN:
				result = SwedishSignalAspect.PROCEED_80;
				break;
			case YELLOW:
				result = SwedishSignalAspect.PROCEED_40_CAUTION;
				break;
			case RED:
				result = SwedishSignalAspect.STOP;
				break;
			case INVALID:
				result = SwedishSignalAspect.FAULTY_RAIL_SIGNALS;
				break;
			default:
				result = SwedishSignalAspect.FAULTY_RAIL_SIGNALS;
				break;
		}

		if (result == SwedishSignalAspect.STOP || result == SwedishSignalAspect.FAULTY_RAIL_SIGNALS) {
			return result;
		}
		
		// Gets the signalBox
		BlockEntityAbstractSignalBox abstractSignalBox = this.getSignalBoxBlockEntity();
		BlockEntitySignalBox signalBox = null;
		// Checks if it is usable
		if(abstractSignalBox == null) {
			return SwedishSignalAspect.FAULTY_RAIL_SIGNALS;
		}else if(abstractSignalBox instanceof BlockEntitySignalBox) {
			signalBox = (BlockEntitySignalBox) abstractSignalBox;
		}

		// If the signal box is the same as itself, show error
		if (signalBox == null || signalBox == this) {
			return SwedishSignalAspect.FAULTY_RAIL_SIGNALS;
		} else if (signalBox != null) {
			result = signalBox.getCurrentAspect();

			if (result != null) {
				switch (result) {
					case STOP, FAULTY_RAIL_SIGNALS:
						result = SwedishSignalAspect.PROCEED_40_CAUTION;
						break;
					case PROCEED_40_CAUTION:
						result = SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;
						break;
					case PROCEED_80_EXPECT_PROCEED_40:
						result = SwedishSignalAspect.PROCEED_80;
						break;
					case PROCEED_80:
						result = SwedishSignalAspect.PROCEED_80;
						break;
					default:
						result = SwedishSignalAspect.FAULTY_RAIL_SIGNALS;
						break;
				}
			}
		}

		return result;
	}
}
