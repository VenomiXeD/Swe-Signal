package venomized.mods.extendedsignals.core.blockentity;

import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalInterpreter;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import java.util.UUID;

public abstract class BlockEntitySignal<T extends ISignalAspect> extends ModelBlockEntity
        implements ISignalTunerToolable, ISignalBlockEntity, ISignalInterpreter<T> {
    private static final String TAG_REFERENCED_SIGNAL_POINT_UUID = "linked_signal_uuid";
    private static final String TAG_SIGNAL_DIRECTION = "signal_direction";

    protected UUID targetEdgePointId;

    @Getter
    private final SignalLighting signalLighting;

    @Getter
    @Nullable
    private Direction.AxisDirection signallingDirection;

    public BlockEntitySignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
        this.signalLighting = constructSignalLighting();
    }

    public static void commonTick(BlockEntitySignal<?> pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        // pBlockEntity.tick++;
    }

    public static void serverTick(BlockEntitySignal<?> pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(pBlockEntity, pLevel, pPos, pBlockState);
    }

    public static void clientTick(BlockEntitySignal<?> be, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(be, pLevel, pPos, pBlockState);
    }

    public abstract SignalLighting constructSignalLighting();

    public SignalStateNode currentSignalState() {
        if (this.getLevel() == null)
            return SignalStateNode.INVALID;
        return ExtendedSignals.sidedNetwork(this.getLevel())
                .getSignalState(targetEdgePointId, signallingDirection == Direction.AxisDirection.POSITIVE);
    }

    public boolean valid() {
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
        if (sourceBlockEntity != null) {
            if (sourceBlockEntity instanceof ISignalBoundaryReferenceProvider sb && useContext.getPlayer() instanceof ServerPlayer serverPlayer) {
                bindToCreateSignal(sb);
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

    public void bindToCreateSignal(ISignalBoundaryReferenceProvider referenceProvider) {
        targetEdgePointId = referenceProvider.getTrackTargetingBehavior().getEdgePoint().getId();
        signallingDirection = referenceProvider.getTrackTargetingBehavior().getTargetDirection();

        if (this.level == null)
            return;

        // If the linked signal has no entry yet, push a new empty dummy raw signal state
        ISignalNetwork network = ExtendedSignals.sidedNetwork(this.level);
        network.updateState(this.targetEdgePointId, signallingDirection == Direction.AxisDirection.POSITIVE, new SignalStateNode());

        this.sync();
    }

    /**
     * @param player
     */
    @Override
    public void onBlockDestroyed(Player player) {
        super.onBlockDestroyed(player);
        // this.unbindFromCreateSignal();
    }

    // public void unbindFromCreateSignal() {
    //     if (getReferencedEdgePoint() != null && getSignalDirection() != null) {
    //         ExtendedSignalsCore.LOGGER.info("Removed mapper for boundary: {}", pointID);
    //         getReferencedEdgePoint().setMapper(getSignalDirection() == Direction.AxisDirection.POSITIVE, null);
    //     }
    // }

    @Override
    public boolean isSource() {
        return false;
    }


    /**
     * @param registries
     * @return
     */
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag syncTag = super.getUpdateTag(registries);
        this.saveAdditional(syncTag, registries);
        return syncTag;
    }

    /**
     * @param tag            The {@link CompoundTag} sent from {@link BlockEntity#getUpdateTag(HolderLookup.Provider)}
     * @param lookupProvider
     */
    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        this.loadAdditional(tag, lookupProvider);
    }

    /**
     * @return
     */
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (targetEdgePointId != null)
            tag.putUUID(TAG_REFERENCED_SIGNAL_POINT_UUID, targetEdgePointId);

        NBTHelp.safeWriteEnum(tag, TAG_SIGNAL_DIRECTION, signallingDirection);
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        targetEdgePointId = tag.hasUUID(TAG_REFERENCED_SIGNAL_POINT_UUID) ? tag.getUUID(TAG_REFERENCED_SIGNAL_POINT_UUID) : null;
        signallingDirection = NBTHelp.safeReadEnum(tag, TAG_SIGNAL_DIRECTION, Direction.AxisDirection.class);
    }

    //    /**
//     * @return
//     */
//    @Override
//    public AABB getRenderBoundingBox() {
//        return super.getRenderBoundingBox().inflate(5);
//    }
}
