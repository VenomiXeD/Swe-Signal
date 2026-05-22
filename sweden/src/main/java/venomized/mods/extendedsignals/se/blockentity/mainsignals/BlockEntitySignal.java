package venomized.mods.extendedsignals.se.blockentity.mainsignals;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.blockentity.ExtendedSignalsCoreBlockEntity;
import venomized.mods.extendedsignals.blockentity.ISignalTunerBindable;
import venomized.mods.extendedsignals.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.RawSignalState;
import venomized.mods.extendedsignals.se.SwedishSignalAspect;
import venomized.mods.extendedsignals.se.signals.ISignalAspect;
import venomized.mods.extendedsignals.util.NBTHelp;
import venomized.mods.extendedsignals.util.SignalUtilities;

import java.util.Optional;
import java.util.UUID;

public abstract class BlockEntitySignal extends ExtendedSignalsCoreBlockEntity
        implements ISignalTunerBindable {
    private static final String TAG_REFERENCED_SIGNAL_EDGE_UUID = "linked_signal_uuid";
    private static final String TAG_SIGNAL_DIRECTION = "signal_direction";

    private Direction.AxisDirection signalDirection;

    private UUID referencedSignalEdgeID;
    private long tick;

    @Getter
    private final SignalLightPlacement[] lights;

    public BlockEntitySignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);

        this.lights = constructLightPlacements();
    }


    protected SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(
                        0, 1, 0, 1, 1, 1
                )
        };
    }

    public static void commonTick(BlockEntitySignal pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        pBlockEntity.tick++;
    }

    public static void serverTick(BlockEntitySignal pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(pBlockEntity, pLevel, pPos, pBlockState);
    }

    public static void clientTick(BlockEntitySignal be, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(be, be.getLevel(), be.getBlockPos(), be.getBlockState());
    }

    public boolean valid() {
        return referencedSignalEdgeID != null;
    }

    public SwedishSignalAspect getCurrentDisplayingAspect() {
        if (referencedSignalEdgeID == null)
            return SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING;

        if (this.signalDirection == null)
            return SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING;

        RawSignalState rawSignalStateState = ExtendedSignalsCore.clientNetworkCache()
                .signalStates()
                .get(referencedSignalEdgeID);

        if (rawSignalStateState == null)
            return SwedishSignalAspect.STOP;

        if (rawSignalStateState.getAxisDirection() != this.signalDirection) {
            return SwedishSignalAspect.STOP;
        }

        return rawSignalStateState.isProceed() ? SwedishSignalAspect.PROCEED_80 : SwedishSignalAspect.STOP;
        // return connectedSignalBox.getCurrentAspect();
    }

    public SignalBlockEntity.SignalState getCurrentDisplayingState() {
        return SignalBlockEntity.SignalState.GREEN;
    }

    public boolean blink() {
        return tick > 10;
    }

    protected void computeSignalLightValues(ISignalAspect aspect) {

    }

    /**
     * Called on the target block entity;
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity
     * @param mode
     * @return
     */
    @Override
    public Pair<InteractionResult, MutableComponent> readerBindingToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
        if (sourceBlockEntity.isPresent()) {
            if (sourceBlockEntity.get() instanceof SignalBlockEntity sb) {
                bindToSignal(sb);
                return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully bound to signal box"));
            }
        }
        return ISignalTunerBindable.super.sourceBindingToReader(sourceBlockEntity, mode);
    }

    private void bindToSignal(SignalBlockEntity id) {
        this.referencedSignalEdgeID = id.getSignal().getId();
        this.signalDirection = id.edgePoint.getTargetDirection();

        ExtendedSignalsCore.LOGGER.info("Axis direction: {}", this.signalDirection);

        this.updateSelf();
    }

    @Override
    public boolean isSource() {
        return false;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for
     * initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in
     * {@link handleUpdateTag}
     */
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag syncTag = super.getUpdateTag();
        this.saveAdditional(syncTag);
        return syncTag;
    }

    /**
     * Called when the chunk's TE update tag, gotten from
     * {@link BlockEntity#getUpdateTag()}, is received on the client.
     * <p>
     * Used to handle this tag in a special way. By default this simply calls
     * {@link BlockEntity#load(CompoundTag)}.
     *
     * @param tag The {@link CompoundTag} sent from
     *            {@link BlockEntity#getUpdateTag()}
     */
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.referencedSignalEdgeID = tag.hasUUID(TAG_REFERENCED_SIGNAL_EDGE_UUID) ? tag.getUUID(TAG_REFERENCED_SIGNAL_EDGE_UUID) : null;
        this.signalDirection = NBTHelp.safeReadEnum(tag, TAG_SIGNAL_DIRECTION, Direction.AxisDirection.class);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        referencedSignalEdgeID = pTag.hasUUID(TAG_REFERENCED_SIGNAL_EDGE_UUID) ? pTag.getUUID(TAG_REFERENCED_SIGNAL_EDGE_UUID) : null;
        this.signalDirection = NBTHelp.safeReadEnum(pTag, TAG_SIGNAL_DIRECTION, Direction.AxisDirection.class);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (referencedSignalEdgeID != null)
            pTag.putUUID(TAG_REFERENCED_SIGNAL_EDGE_UUID, referencedSignalEdgeID);
        NBTHelp.safeWriteEnum(pTag, TAG_SIGNAL_DIRECTION, signalDirection);
    }
}
