package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;
import venomized.mods.extendedsignals.core.Global;
import venomized.mods.extendedsignals.core.create.tracks.CoreEdgePoints;
import venomized.mods.extendedsignals.core.create.tracks.PathTrainDetector;

import java.util.List;

public class BlockEntityPathTrainDetector extends SmartBlockEntity implements ISignalTunerToolable {
    private TrackTargetingBehaviour<PathTrainDetector> pathTrainDetector;
    private ScrollValueBehaviour pathDistanceScrollValue;

    public int redstoneOutput = 0;

    public BlockEntityPathTrainDetector(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(pathTrainDetector = new TrackTargetingBehaviour<>(this, CoreEdgePoints.PATH_TRAIN_DETECTOR));
        pathDistanceScrollValue = new ScrollValueBehaviour(
                Component.translatable("setting.extendedsignals.pathtraindetector.distance"), this,
                new CenteredSideValueBoxTransform((s, d) -> switch (d) {
                    case NORTH, WEST, SOUTH, EAST -> true;
                    default -> false;
                })
        );
        pathDistanceScrollValue.between(1, Global.SCAN_DISTANCE);
        pathDistanceScrollValue.withCallback(this::pathDetectionRangeChanged);
        behaviours.add(pathDistanceScrollValue);

        // timerDistanceScrollValue = new ScrollValueBehaviour(
        //         Component.translatable("setting.extendedsignals.pathtraindetector.timer"), this,
        //         new CenteredSideValueBoxTransform((s,d)-> switch (d) {
        //             case UP, DOWN -> true;
        //             default -> false;
        //         })
        // );
        // timerDistanceScrollValue.between(0, 10);
        // timerDistanceScrollValue.withCallback(this::timerCountdownChanged);
        //behaviours.add(timerDistanceScrollValue);
    }

    /**
     * @param mode
     * @param context
     * @return
     */
    @Override
    public InteractionResult onSignalToolInteract(SignalTunerMode mode, UseOnContext context) {
        if (context.getLevel().isClientSide())
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());

        switch (mode) {
            case CONFIGURE:
                pathTrainDetector.getEdgePoint().deactivationDelay += 20;
                if (pathTrainDetector.getEdgePoint().deactivationDelay > 5 * 20)
                    pathTrainDetector.getEdgePoint().deactivationDelay = 0;

                context.getPlayer()
                        .sendSystemMessage(
                                Component.translatable(
                                        "setting.extendedsignals.pathtraindetector.timer",
                                        pathTrainDetector.getEdgePoint().deactivationDelay / 20
                                )
                        );
                return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void pathDetectionRangeChanged(Integer integer) {
        pathTrainDetector.getEdgePoint().triggerDistance = integer;
    }

    public void trainInbound() {
        redstoneOutput = 15;
        level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
    }

    public void trainOutbound() {
        redstoneOutput = 0;
        level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
    }
}
