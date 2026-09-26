package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;
import venomized.mods.extendedsignals.core.blockentity.ITrackEdgePointHolder;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;

import java.util.UUID;

@Mixin(value = SignalBlockEntity.class, remap = false)
public abstract class MixinSignalBlockEntity extends SmartBlockEntity implements ISignalTunerToolable, ITrackEdgePointHolder, ISignalBlockEntity {
    @Shadow
    public TrackTargetingBehaviour<SignalBoundary> edgePoint;

    public MixinSignalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
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

        SignalBoundary boundary = edgePoint.getEdgePoint();
        ISignal<?> signalPoint = (ISignal<?>) boundary;
        boolean primary = edgePoint.getTargetDirection() == Direction.AxisDirection.POSITIVE;

        switch (mode) {
            case CONFIGURE:
                boolean old = signalPoint.isMainSignal(primary);
                signalPoint.setMainSignal(primary, !old);

                context.getPlayer()
                        .sendSystemMessage(Component.translatable(
                                "message.extended_signals.createsignal.tool.config.chaining", !old
                        ));

                return InteractionResult.SUCCESS;
            case INFO:
                SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get((boundary).groups.get(primary));
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
                                    reservedByTrainId, train.name, Mth.sqrt(train.distanceToLocationSqr(level, getBlockPos().getCenter())))
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

    /**
     * @param level
     * @param entity
     * @param useItem
     * @param itemStack
     * @return
     */
    @Override
    public InteractionResult onRightClick(Level level, Player entity, TriState useItem, ItemStack itemStack) {
        if (itemStack.is(Tags.Items.GEMS_QUARTZ)) {
            if (!level.isClientSide()) {
                boolean front = getTrackTargetingBehavior().getTargetDirection() == Direction.AxisDirection.POSITIVE;
                ISignal<?> signal = (ISignal<?>) getTrackTargetingBehavior().getEdgePoint();
                boolean current = signal.isBlockSignalMode(front);
                signal.setBlockSignalMode(front, !current);

                entity.sendSystemMessage(Component.literal("block signal mode: " + (!current ? "yes" : "no")));
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
