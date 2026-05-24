package venomized.mods.extendedsignals.core.blockentity;

import com.simibubi.create.api.contraption.transformable.TransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.create.tracks.RepeaterSignal;

import java.util.List;
import java.util.UUID;

public class BlockEntityRepeaterCreateSignal extends SmartBlockEntity implements TransformableBlockEntity, ISignalTunerToolable, ISignalBoundaryReferenceProvider {
    public TrackTargetingBehaviour<RepeaterSignal> repeaterSignal;

    public BlockEntityRepeaterCreateSignal(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @param blockEntity
     * @param transform
     */
    @Override
    public void transform(BlockEntity blockEntity, StructureTransform transform) {
        repeaterSignal.transform(blockEntity, transform);
    }

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(repeaterSignal = new TrackTargetingBehaviour<>(this, RepeaterSignal.REPEATER));
    }

    /**
     * @return
     */
    @Override
    public UUID id() {
        return repeaterSignal.getEdgePoint().getId();
    }

    /**
     * @return
     */
    @Override
    public Direction.AxisDirection direction() {
        return this.repeaterSignal.getTargetDirection();
    }


}
