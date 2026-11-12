package venomized.mods.extendedsignals.se.blockentity;

import com.simibubi.create.api.contraption.transformable.TransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.IEdgePointBlockEntity;
import venomized.mods.extendedsignals.se.create.tracks.ATCBalise;
import venomized.mods.extendedsignals.se.create.tracks.SwedenEdgePoints;

import java.util.List;

public class BlockEntityATCController extends SmartBlockEntity implements TransformableBlockEntity, IEdgePointBlockEntity {
    public TrackTargetingBehaviour<ATCBalise> atcControllerPoint;

    public BlockEntityATCController(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(atcControllerPoint = new TrackTargetingBehaviour<>(this, SwedenEdgePoints.BALISE));
    }

    /**
     * @param blockEntity
     * @param structureTransform
     */
    @Override
    public void transform(BlockEntity blockEntity, StructureTransform structureTransform) {
        atcControllerPoint.transform(blockEntity, structureTransform);
    }

    /**
     * @return
     */
    @Override
    public TrackTargetingBehaviour<?> getTrackTargetingBehavior() {
        return atcControllerPoint;
    }
}
