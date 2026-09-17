package venomized.mods.extendedsignals.core.blockentity;

import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalInterpreter;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.BlockEntityReference;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import java.util.UUID;

public abstract class BlockEntitySignal<T extends ISignalAspect> extends ModelBlockEntity
        implements ISignalTunerToolable, ISignalBlockEntity, ISignalInterpreter<T> {
    @Getter
    private final SignalContainer signalContainer;
    protected UUID targetEdgePointId;
    @Getter
    private ISignalAspect currentDisplayedAspect;
    @Getter
    @Nullable
    private Direction.AxisDirection signallingDirection;
    @NotNull
    private final BlockEntityReference<BlockEntity> signalSource = new BlockEntityReference<>(BlockEntity.class);

    public BlockEntitySignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
        signalContainer = new SignalContainer();
        configureSignalLights(signalContainer);
    }

    public static void clientTick(BlockEntitySignal<?> be, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        be.currentDisplayedAspect = be.interpret(be.currentSignalState(), be.getSignallingDirection());
    }

    public abstract void configureSignalLights(SignalContainer signalLights);

    public SignalStateNode currentSignalState() {
        if (this.getLevel() == null)
            return SignalStateNode.INVALID;
        return ExtendedSignals.sidedNetwork(this.getLevel())
                .getSignalState(targetEdgePointId, signallingDirection == Direction.AxisDirection.POSITIVE);
    }

    public boolean isSignalValid() {
        if (this.getLevel() == null)
            return false;

        if (signallingDirection == null)
            return false;

        if (targetEdgePointId == null)
            return false;

        return ExtendedSignals.sidedNetwork(this.getLevel())
                .signalStates()
                .containsKey(targetEdgePointId);
    }


    /**
     * Called on the target block entity;
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity
     * @param mode
     * @param useContext
     * @return
     */
    @Override
    public InteractionResult readerBindingToSource(@UnknownNullability ISignalTunerToolable sourceBlockEntity, SignalTunerMode mode, UseOnContext useContext) {
        if (sourceBlockEntity instanceof ITrackEdgePointHolder sb) {
            configureFromSignal(sb);
            if (this.targetEdgePointId == null)
                return InteractionResult.PASS;

            useContext.getPlayer().displayClientMessage(
                    Component.translatable("message.extendedsignals.blockentitysignal.bind.success", targetEdgePointId.toString())
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)
                            ),
                    true
            );

            return InteractionResult.SUCCESS;
        }

        return ISignalTunerToolable.super.sourceBindingToReader(sourceBlockEntity, mode, useContext);
    }

    /**
     * @param mode
     * @param context
     * @return
     */
    @Override
    public InteractionResult onSignalToolInteract(SignalTunerMode mode, UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.PASS;
        }

        switch (mode) {
            case DISCONNECT:
                this.targetEdgePointId = null;
                this.signallingDirection = null;
                context.getPlayer().sendSystemMessage(
                        Component.translatable("message.extendedsignals.blockentitysignal.disconnect.success")
                );
                sync();
                return InteractionResult.SUCCESS;
            case CONNECT:
                return InteractionResult.PASS;
        }

        context.getPlayer().sendSystemMessage(
                Component.translatable("message.extendedsignals.blockentitysignal.tool.unknown")
        );

        return InteractionResult.PASS;
    }

    public void configureFromSignal(ITrackEdgePointHolder signalProvider) {
        targetEdgePointId = signalProvider.getTrackTargetingBehavior().getEdgePoint().getId();
        signallingDirection = signalProvider.getTrackTargetingBehavior().getTargetDirection();

        boolean front = signallingDirection == Direction.AxisDirection.POSITIVE;

        if (this.level == null)
            return;

        // If the linked signal has no entry yet, push a new empty dummy raw signal state
        ISignalNetwork network = ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY;
        network.updateState(targetEdgePointId, front, new SignalStateNode());

        if (signalProvider.getTrackTargetingBehavior().getEdgePoint() instanceof ISignal<?> signal) {
            signal.setMainSignal(front, hasMainSignalCapability());
        }

        signalSource.newTarget((BlockEntity) signalProvider);

        this.sync();
    }

    /**
     * @param level
     * @param player
     */
    @Override
    public void onBlockDestroyed(Level level, Player player) {
        if (level.isClientSide()) {
            return;
        }
        signalSource.getReference(level).ifPresent(blockEntity -> {
            if (blockEntity instanceof ITrackEdgePointHolder signalEdgePointProvider) {
                boolean front = signallingDirection == Direction.AxisDirection.POSITIVE;
                if (signalEdgePointProvider.getTrackTargetingBehavior().getEdgePoint() instanceof ISignal<?> signal) {
                    signal.setMainSignal(front, false);
                }
            }
            signalSource.newTarget(null);
        });
    }

    @Override
    public boolean isSource() {
        return false;
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (targetEdgePointId != null)
            tag.putUUID("linked_signal_uuid", targetEdgePointId);

        NBTHelp.safeWriteEnum(tag, "signal_direction", signallingDirection);
        tag.put("signal_source_position", signalSource.toNBT(registries));
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        targetEdgePointId = tag.hasUUID("linked_signal_uuid") ? tag.getUUID("linked_signal_uuid") : null;
        signallingDirection = NBTHelp.safeReadEnum(tag, "signal_direction", Direction.AxisDirection.class);
        signalSource.fromNBT(tag.getCompound("signal_source_position"), registries);
    }

    public abstract boolean hasMainSignalCapability();

    //    /**
//     * @return
//     */
//    @Override
//    public AABB getRenderBoundingBox() {
//        return super.getRenderBoundingBox().inflate(5);
//    }
}
