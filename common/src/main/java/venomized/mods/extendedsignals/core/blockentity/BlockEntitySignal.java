package venomized.mods.extendedsignals.core.blockentity;

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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.*;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import java.util.Optional;
import java.util.UUID;

public abstract class BlockEntitySignal<T extends ISignalAspect> extends ExtendedSignalsCoreBlockEntity
        implements ISignalTunerBindable, ISignalBlockEntity, ISignalInterpreter<T> {
    private static final String TAG_REFERENCED_SIGNAL_EDGE_UUID = "linked_signal_uuid";
    private static final String TAG_SIGNAL_DIRECTION = "signal_direction";

    private Direction.AxisDirection signalDirection;

    private UUID referencedSignalEdgeID;
    private long tick;

    @Getter
    protected final SignalLightPlacement[] lights;
    @Getter
    protected final SignalLightState[] lightStates;

    public BlockEntitySignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);

        this.lights = constructLightPlacements();
        this.lightStates = new SignalLightState[lights.length];
        for (int i = 0; i < lightStates.length; i++) {
            lightStates[i] = new SignalLightState();
        }
    }

    @NotNull
    protected static SignalLightPlacement[] getSignalLightPlacements(
            final double x, final double z, final float xScale, final float yScale, final float zScale, final double initialY, final double deltaY, final int lightCount
    ) {

        SignalLightPlacement[] signalLights = new SignalLightPlacement[lightCount];
        for (int i = 0; i < lightCount; i++) {
            signalLights[i] = new SignalLightPlacement(
                    x, initialY + deltaY * i, z,
                    xScale, yScale, zScale
            );
        }

        return signalLights;
    }


    public SignalLightPlacement[] constructLightPlacements() {
        if (!(this.getBlockState()
                .getBlock() instanceof BlockSignal bs))
            throw new UnsupportedOperationException(
                    "Cannot construct signal placement since the underlying block is not a uniform light  BlockSignal; Requires overriding constructLightPlacements()");

        return getSignalLightPlacements(
                bs.lightXPosition(), bs.lightZPosition(), bs.lightXScale(), bs.lightYScale(), bs.lightZScale(),
                bs.lightYPosition(), bs.lightSeparationDistance(),
                bs.getSignalLightCount()
        );
    }

    public static void commonTick(BlockEntitySignal<?> pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        pBlockEntity.tick++;
    }

    public static void serverTick(BlockEntitySignal<?> pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(pBlockEntity, pLevel, pPos, pBlockState);
    }

    public static void clientTick(BlockEntitySignal<?> be, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(be, pLevel, pPos, pBlockState);

        if (!be.valid() && be.tick % 20 == 0) {
            for (SignalLightState lightState : be.lightStates) {
                lightState.setColorDirect(
                        pLevel.random.nextFloat(), pLevel.random.nextFloat(), pLevel.random.nextFloat()
                );
            }
            return;
        }
        RawSignalState rawSignalState = be.currentSignalState();
        if (rawSignalState.getAxisDirection() != be.signalDirection)
            return;
        ISignalAspect aspect = be.interpret(rawSignalState);
        if (aspect == null) {
            return;
        }
        aspect.applyAspect(be.tick, be.lightStates);
    }


    public RawSignalState currentSignalState() {
        if (this.getLevel() == null)
            return RawSignalState.INVALID;
        return ExtendedSignalsCore.sidedNetwork(this.getLevel())
                .signalStates()
                .getOrDefault(this.referencedSignalEdgeID, RawSignalState.INVALID);
    }

    public boolean valid() {
        if (this.getLevel() == null)
            return false;

        return referencedSignalEdgeID != null && ExtendedSignalsCore.sidedNetwork(this.getLevel())
                .signalStates()
                .containsKey(referencedSignalEdgeID);
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

    /**
     * @param nbt
     */
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }
}
