package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.api.contraption.transformable.TransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;
import venomized.mods.extendedsignals.core.create.tracks.CoreEdgePoints;
import venomized.mods.extendedsignals.core.create.tracks.points.PathTrainDetector;
import venomized.mods.extendedsignals.core.util.TrackedValue;

import java.util.List;

public class BlockEntityTrainPathObserver extends SmartBlockEntity implements ISignalTunerToolable, TransformableBlockEntity {
    private final TrackedValue<Boolean> trainPresentDetector = new TrackedValue<>(false, this::trainPresenceChanged);
    public int redstoneOutput = 0;
    private TrackTargetingBehaviour<PathTrainDetector> pathTrainDetector;
    private ScrollValueBehaviour pathDistanceScrollValue;
    private int deactivationDelay;


    public BlockEntityTrainPathObserver(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
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
                new CenteredSideValueBoxTransform((s, d) -> true)
        );
        pathDistanceScrollValue.between(1, (int) ExtendedSignalsConfig.SERVER.defaultScanDistance.get().doubleValue());
        pathDistanceScrollValue.withCallback(this::pathDetectionRangeChanged);
        behaviours.add(pathDistanceScrollValue);
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
                deactivationDelay += 20;
                if (deactivationDelay > 5 * 20)
                    deactivationDelay = 0;

                context.getPlayer()
                        .sendSystemMessage(
                                Component.translatable(
                                        "setting.extendedsignals.pathtraindetector.timer",
                                        deactivationDelay / 20
                                )
                        );
                return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void pathDetectionRangeChanged(int val) {
        pathTrainDetector.getEdgePoint().triggerDistance = val;
    }

    /**
     * @param blockEntity
     * @param transform
     */
    @Override
    public void transform(BlockEntity blockEntity, StructureTransform transform) {
        pathTrainDetector.transform(blockEntity, transform);
    }

    /**
     *
     */
    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide())
            return;

        if (pathTrainDetector.getEdgePoint() == null)
            return;

        trainPresentDetector.change(pathTrainDetector.getEdgePoint().trainPresent());
    }

    private void trainPresenceChanged(boolean from, boolean to) {
        redstoneOutput = to ? 15 : 0;
        getLevel().updateNeighborsAt(worldPosition, getBlockState().getBlock());
    }
}
