package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import venomized.mods.extendedsignals.core.blockentity.ISignalBoundaryReferenceProvider;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;

import java.util.UUID;

@Mixin(value = SignalBlockEntity.class, remap = false)
public abstract class MixinCreateTrainSignal extends SmartBlockEntity implements ISignalTunerToolable, ISignalBoundaryReferenceProvider {
    @Shadow
    public TrackTargetingBehaviour<SignalBoundary> edgePoint;

    public MixinCreateTrainSignal(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean isReader() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public TrackTargetingBehaviour<?> getTrackTargetingBehavior() {
        return this.edgePoint;
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

        IExtendedSignalBoundary<SignalBoundary> boundary = (IExtendedSignalBoundary<SignalBoundary>) edgePoint.getEdgePoint();
        boolean primary = edgePoint.getTargetDirection() == Direction.AxisDirection.POSITIVE;

        switch (mode) {
            case CONFIGURE:
                boolean old = boundary.getChainingSkipped(primary);
                boundary.setChainingSkipped(primary, !old);

                context.getPlayer()
                        .sendSystemMessage(Component.translatable(
                                "message.extended_signals.createsignal.tool.config.chaining", !old
                        ));

                return InteractionResult.SUCCESS;
            case INFO:
                SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get(((SignalBoundary) boundary).groups.get(primary));
                if (signalEdgeGroup == null) {
                    context.getPlayer().sendSystemMessage(
                            Component.translatable("message.extended_signals.createsignal.tool.info.no_group")
                    );
                    return InteractionResult.FAIL;
                }

                UUID reservedByTrainId = ((ISignalEdgeGroup) signalEdgeGroup).extendedSignals$reservedByTrain();
                Train train = Create.RAILWAYS.trains.get(reservedByTrainId);
                if (train == null) {
                    context.getPlayer().sendSystemMessage(
                            Component.translatable("message.extended_signals.createsignal.tool.info.owning_train_nonexistent")
                    );
                    return InteractionResult.FAIL;
                }
                if (reservedByTrainId != null) {
                    context.getPlayer().sendSystemMessage(
                            Component.translatable("message.extended_signals.createsignal.tool.info.owning_train",
                                    reservedByTrainId, train.name, train.distanceToLocationSqr(level, getBlockPos().getCenter()))
                    );
                } else {
                    context.getPlayer().sendSystemMessage(
                            Component.translatable("message.extended_signals.createsignal.tool.info.no_owning_train")
                    );
                }
                break;
        }

        return InteractionResult.PASS;
    }
}
