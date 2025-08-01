package venomized.mc.mods.swsignals.blockentity.se;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.blockentity.BlockEntityAbstractSignalBox;
import venomized.mc.mods.swsignals.blockentity.ITickingEntity;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

import java.util.HashMap;
import java.util.Map;

public class BlockEntitySignalBox extends BlockEntityAbstractSignalBox implements ITickingEntity<BlockEntitySignalBox> {
	public static final String NAME = "be_sw_signal_box";
	private final HashMap<SwedishSignalAspect, SwedishSignalAspect> manualOverrides = new HashMap<>();
	private SwedishSignalAspect aspect;
	private SwedishSignalAspect previousAspect;

	public BlockEntitySignalBox(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

	public SwedishSignalAspect getCurrentAspect() {
		if (this.level.isClientSide()) {
			return aspect;
		}

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
			SwedishSignalAspect nextSignal = signalBox.getCurrentAspect();

			if (nextSignal != null) {
				result = switch (nextSignal) {
					case STOP, SIGNAL_FAULT_INCORRECT_WIRING -> SwedishSignalAspect.PROCEED_40_CAUTION;
					case PROCEED_40_SHORT, PROCEED_40_CAUTION -> SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;
					case PROCEED_80_EXPECT_STOP, PROCEED_80, PROCEED_80_EXPECT_PROCEED_80 ->
							SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_80;
					case PROCEED_80_EXPECT_PROCEED_40 -> SwedishSignalAspect.PROCEED_80;
				};
			}

		}
		return result;
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		if (this.aspect != null) {
			pTag.putInt("self_signal_spect", this.aspect.ordinal());
		}
		CompoundTag overrideTag = new CompoundTag();
		for (Map.Entry<SwedishSignalAspect, SwedishSignalAspect> swedishSignalAspectSwedishSignalAspectEntry : this.manualOverrides.entrySet()) {
			NBTHelper.writeEnum(overrideTag, swedishSignalAspectSwedishSignalAspectEntry.getKey().name(), swedishSignalAspectSwedishSignalAspectEntry.getValue());
		}

		pTag.put("override", overrideTag);
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		if (pTag.contains("self_signal_spect")) {
			this.aspect = SwedishSignalAspect.values()[pTag.getInt("self_signal_spect")];
		}

		CompoundTag overrideTag = pTag.getCompound("override");
		for (String key : overrideTag.getAllKeys()) {
			this.manualOverrides.put(SwedishSignalAspect.valueOf(key), NBTHelper.readEnum(overrideTag, key, SwedishSignalAspect.class));
		}
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state, BlockEntitySignalBox blockEntity) {
		this.aspect = this.getCurrentAspect();
		if (this.previousAspect != aspect) {
			this.previousAspect = aspect;
			this.updateSelf();
		}
	}
}
