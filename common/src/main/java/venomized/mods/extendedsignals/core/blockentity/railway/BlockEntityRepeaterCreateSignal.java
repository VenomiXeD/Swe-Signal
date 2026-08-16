package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.api.contraption.transformable.TransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;
import venomized.mods.extendedsignals.core.create.tracks.points.CoreEdgePoints;
import venomized.mods.extendedsignals.core.create.tracks.points.RepeaterSignalEdgePoint;

import java.util.List;

public class BlockEntityRepeaterCreateSignal extends SmartBlockEntity implements TransformableBlockEntity, ISignalTunerToolable, ISignalBoundaryReferenceProvider {
    public TrackTargetingBehaviour<RepeaterSignalEdgePoint> repeaterSignal;

    public BlockEntityRepeaterCreateSignal(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @return
     */
    @Override
    public boolean isReader() {
        return false;
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
        behaviours.add(repeaterSignal = new TrackTargetingBehaviour<>(this, CoreEdgePoints.REPEATER));
    }

    /**
     * @return
     */
    @Override
    public TrackTargetingBehaviour<?> getTrackTargetingBehavior() {
        return repeaterSignal;
    }

    /**
     * @param mode
     * @param context
     * @return
     */
    @Override
    public InteractionResult onSignalToolInteract(SignalTunerMode mode, UseOnContext context) {
        if (context.getLevel().isClientSide())
            return InteractionResult.PASS;

        if (mode == SignalTunerMode.CONFIGURE) {


            TrackEdge connection = repeaterSignal.determineGraphLocation().graph
                    .getConnection(repeaterSignal.determineGraphLocation().edge.map(repeaterSignal.determineGraphLocation().graph::locateNode));

            TravellingPoint p = new TravellingPoint(
                    connection.node1,
                    connection.node2,
                    connection,
                    repeaterSignal.determineGraphLocation().position,
                    false
            );

            p.travel(
                    repeaterSignal.determineGraphLocation().graph,
                    256,
                    p.steer(
                            TravellingPoint.SteerDirection.NONE,
                            new Vec3(0, 1, 0)
                    ), (a, b) -> {
                        if (b.getFirst() instanceof SignalBoundary sb) {
                            context.getPlayer().sendSystemMessage(
                                    Component.literal("found signal")
                            );
                            return true;
                        }
                        return false;
                    }
            );

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
