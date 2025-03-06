package venomized.mc.mods.swsignals.blockentity;

import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.create.tracks.ATCController;

import java.util.Collection;
import java.util.List;

public class BlockEntityATCController extends SmartBlockEntity implements ITransformableBlockEntity {
	public TrackTargetingBehaviour<ATCController> atcControllerPoint;

	public BlockEntityATCController(BlockPos pos, BlockState state) {
		super(SwBlockEntities.BE_ATC_CONTROLLER.get(), pos, state);
	}

	@Override
	public void transform(StructureTransform transform) {
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		atcControllerPoint = new TrackTargetingBehaviour<>(this, ATCController.ATC);
		behaviours.add(atcControllerPoint);
	}

	@Override
	public Collection<BlockEntityBehaviour> getAllBehaviours() {
		return super.getAllBehaviours();
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return super.getUpdatePacket();
	}

	@Override
	public BlockEntityType<?> getType() {
		return super.getType();
	}
}
