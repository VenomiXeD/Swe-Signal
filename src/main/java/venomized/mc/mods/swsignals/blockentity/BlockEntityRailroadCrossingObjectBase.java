package venomized.mc.mods.swsignals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BlockEntityRailroadCrossingObjectBase extends SwBlockEntityBase implements ISignalTunerBindable {
	@Nullable
	public BlockPos railroadCrossingControllerPos;

	public BlockEntityRailroadCrossingObjectBase(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
		super(pType, pPos, pBlockState);
	}

	/**
	 * Signal Box A -> Create Signal; Create Signal is the source
	 *
	 * @param sourceBlockEntity source block destination
	 * @param mode
	 */
	@Override
	public void onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
		sourceBlockEntity.ifPresent(be->{
			if (be instanceof BlockEntityRailroadCrossingController c) {
				railroadCrossingControllerPos = c.getBlockPos();
			}
		});
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
