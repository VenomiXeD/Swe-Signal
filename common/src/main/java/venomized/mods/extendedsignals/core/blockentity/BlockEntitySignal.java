package venomized.mods.extendedsignals.core.blockentity;

import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.ISignalInterpreter;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.NBTHelp;

import java.util.UUID;

public abstract class BlockEntitySignal<T extends ISignalAspect> extends CoreBlockEntity
        implements ISignalTunerToolable, ISignalBlockEntity, ISignalInterpreter<T> {
    private static final String TAG_REFERENCED_SIGNAL_POINT_UUID = "linked_signal_uuid";
    private static final String TAG_REFERENCED_SIGNAL_POINT_TYPE = "linked_signal_type";

    private static final String TAG_SIGNAL_DIRECTION = "signal_direction";
    @Getter
    protected final SignalLightPlacement[] lights;
    @Getter
    protected final SignalLightState[] lightStates;
    protected UUID pointID;

    @Getter
    @Nullable
    private Direction.AxisDirection signallingDirection;

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

    public static void commonTick(BlockEntitySignal<?> pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        // pBlockEntity.tick++;
    }

    public static void serverTick(BlockEntitySignal<?> pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(pBlockEntity, pLevel, pPos, pBlockState);
    }

    public static void clientTick(BlockEntitySignal<?> be, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(be, pLevel, pPos, pBlockState);
    }

    public abstract SignalLightPlacement[] constructLightPlacements();

    public SignalStateNode currentSignalState() {
        if (this.getLevel() == null)
            return SignalStateNode.INVALID;
        return ExtendedSignalsCore.sidedNetwork(this.getLevel())
                .getSignalState(pointID, signallingDirection == Direction.AxisDirection.POSITIVE);
    }

    public boolean valid() {
        if (this.getLevel() == null)
            return false;

        if (signallingDirection == null)
            return false;

        if (pointID == null)
            return false;

        return ExtendedSignalsCore.sidedNetwork(this.getLevel())
                .signalStates()
                .containsKey(pointID);
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
            if (sourceBlockEntity instanceof ISignalBoundaryReferenceProvider sb) {
                bindToCreateSignal(sb);
                if (this.pointID == null)
                    return InteractionResult.PASS;

                useContext.getPlayer().sendSystemMessage(
                        Component.translatable("message.extendedsignals.blockentitysignal.bind.success", pointID)
                                .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)
                                )
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
                this.pointID = null;
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
        pointID = referenceProvider.getTrackTargetingBehavior().getEdgePoint().getId();
        signallingDirection = referenceProvider.getTrackTargetingBehavior().getTargetDirection();

        if (this.level == null)
            return;

        // If the linked signal has no entry yet, push a new empty dummy raw signal state
        ISignalNetwork network = ExtendedSignalsCore.sidedNetwork(this.level);
        network.updateState(this.pointID, signallingDirection == Direction.AxisDirection.POSITIVE, new SignalStateNode());

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
     * Get an NBT compound to sync to the client with SPacketChunkData, used for
     * initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in
     * {@link handleUpdateTag}
     */
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag syncTag = super.getUpdateTag();
        this.saveAdditional(syncTag);
        return syncTag;
    }

    /**
     * @return
     */
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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
        pointID = tag.hasUUID(TAG_REFERENCED_SIGNAL_POINT_UUID) ? tag.getUUID(TAG_REFERENCED_SIGNAL_POINT_UUID) : null;
        signallingDirection = NBTHelp.safeReadEnum(tag, TAG_SIGNAL_DIRECTION, Direction.AxisDirection.class);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        pointID = pTag.hasUUID(TAG_REFERENCED_SIGNAL_POINT_UUID) ? pTag.getUUID(TAG_REFERENCED_SIGNAL_POINT_UUID) : null;
        signallingDirection = NBTHelp.safeReadEnum(pTag, TAG_SIGNAL_DIRECTION, Direction.AxisDirection.class);

        // if (pTag.contains(TAG_REFERENCED_SIGNAL_POINT_TYPE))
        //     this.pointType = EdgePointType.TYPES.get(NBTHelper.readResourceLocation(pTag, TAG_REFERENCED_SIGNAL_POINT_TYPE));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (pointID != null)
            pTag.putUUID(TAG_REFERENCED_SIGNAL_POINT_UUID, pointID);

        NBTHelp.safeWriteEnum(pTag, TAG_SIGNAL_DIRECTION, signallingDirection);

        // if (pointType != null)
        //     NBTHelper.writeResourceLocation(pTag, TAG_REFERENCED_SIGNAL_POINT_TYPE, pointType.getId());
    }

    /**
     * @return
     */
    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox().inflate(5);
    }
}
