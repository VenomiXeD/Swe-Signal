package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class BlockEntityRailroadCrossingController extends SwBlockEntityBase implements ISignalTunerBindable {
	public BlockEntityRailroadCrossingController(BlockPos pPos, BlockState pBlockState) {
		super(SwBlockEntities.BE_RAILROAD_CROSSING_CONTROLLER.get(), pPos, pBlockState);
	}

	/**
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity source block destination
	 * @param mode
	 */
	@Override
	public void onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {

	}

	/**
	 * Signal Box A -> Create Signal; Signal Box A is the target
	 *
	 * @param targetBlockEntity target block destination
	 * @param mode
	 */
	@Override
	public void onBindToTarget(Optional<ISignalTunerBindable> targetBlockEntity, SignalTunerMode mode) {

	}
}
