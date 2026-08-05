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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.create.tracks.CoreEdgePoints;
import venomized.mods.extendedsignals.core.create.tracks.points.SpeedModifier;
import venomized.mods.extendedsignals.core.util.TrainHelp;

import java.util.List;

public class BlockEntitySignalSpeedModifier extends SmartBlockEntity implements TransformableBlockEntity {
    private TrackTargetingBehaviour<SpeedModifier> edgePoint;

    private ScrollValueBehaviour speedConfigure;

    public BlockEntitySignalSpeedModifier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

    }

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, CoreEdgePoints.SPEED_MODIFIER));
        speedConfigure = new ScrollValueBehaviour(
                Component.translatable("tracks.extended_signals.speedmodifier.value"),
                this,
                new CenteredSideValueBoxTransform()
        );
        speedConfigure.between(1, (int) TrainHelp.absoluteTopSpeedForTrainsKph());
        speedConfigure.withCallback(this::speedConfigured);
        behaviours.add(speedConfigure);
    }

    private void speedConfigured(int integer) {
        edgePoint.getEdgePoint().setSpeedModifierKph(integer);
    }

    /**
     * @param blockEntity
     * @param transform
     */
    @Override
    public void transform(BlockEntity blockEntity, StructureTransform transform) {
        edgePoint.transform(blockEntity, transform);
    }

    public InteractionResult onWrenched() {
        edgePoint.getEdgePoint().onWrenched();
        return InteractionResult.SUCCESS;
    }
}
