package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.create.tracks.ATCController;

import java.util.List;

public class BlockEntityATCController extends SmartBlockEntity implements ITransformableBlockEntity {
	public TrackTargetingBehaviour<ATCController> atcControllerPoint;

	public BlockEntityATCController(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void transform(StructureTransform transform) {
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		atcControllerPoint = new TrackTargetingBehaviour<>(this, ATCController.ATC);
		behaviours.add(atcControllerPoint);
	}
}
