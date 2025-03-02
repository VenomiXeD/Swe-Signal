package venomized.mc.mods.swsignals.blockentity.sw;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
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

	public SignalBlockEntity getCreateSignalBlockEntity() {
		if (createSignalBlockEntityPosition == null) {
			return null;
		}
		BlockEntity blockEntity = this.getLevel().getBlockEntity(createSignalBlockEntityPosition);
		if (blockEntity instanceof SignalBlockEntity) {
			return (SignalBlockEntity) blockEntity;
		}
		return null;
	}

	public SwedishSignalAspect getCurrentAspect() {
		SwedishSignalAspect result;

		SignalBlockEntity b = this.getCreateSignalBlockEntity();
		if (b == null) {
			return null;
		} else if (b.getSignal() == null) {
			return cachedAspect;
		}

		SignalBlockEntity.SignalState state = b.getState();
		result = switch (state) {
			case GREEN -> SwedishSignalAspect.PROCEED_80;
			case RED -> SwedishSignalAspect.STOP;
			case YELLOW -> SwedishSignalAspect.PROCEED_40_CAUTION;
			case INVALID -> null;
		};

		this.cachedAspect = result;
		return result;
	}
}
