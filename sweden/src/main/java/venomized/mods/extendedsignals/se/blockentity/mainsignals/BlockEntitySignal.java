package venomized.mods.extendedsignals.se.blockentity.mainsignals;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import it.unimi.dsi.fastutil.Pair;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.blockentity.ExtendedSignalsCoreBlockEntity;
import venomized.mods.extendedsignals.blockentity.ISignalTunerBindable;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.RawSignalState;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.SwedishSignalAspect;
import venomized.mods.extendedsignals.util.SignalUtilities;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BlockEntitySignal extends ExtendedSignalsCoreBlockEntity
        implements IHaveGoggleInformation, ISignalTunerBindable {
    private static final String TAG_REFERENCED_SIGNAL_UUID = "linked_signal_uuid";
    private final int lightCount;
    public float[] lightLevels;
    private UUID referencedSignalUUID;
    private int tick;
    private int remainingTicksAspectChangeDelay;

    public BlockEntitySignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState, int lightCount) {
        super(t, pPos, pBlockState);
        this.lightCount = lightCount;
        if (lightCount != -1) {
            this.lightLevels = new float[lightCount];
        }
    }

    public static void commonTick(BlockEntitySignal pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        pBlockEntity.tick = (pBlockEntity.tick + 1) % 20;
        pBlockEntity.remainingTicksAspectChangeDelay = Math.max(0, pBlockEntity.remainingTicksAspectChangeDelay - 1);

    }

    public static void serverTick(BlockEntitySignal pBlockEntity, Level pLevel, BlockPos pPos, BlockState pBlockState) {
        commonTick(pBlockEntity, pLevel, pPos, pBlockState);
    }

    public static void clientTick(BlockEntitySignal be, SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
        commonTick(be, be.getLevel(), be.getBlockPos(), be.getBlockState());
        be.computeSignalLightValues(aspect, createSignalState, doInvalidBlinking);
    }

    public int getLightCount() {
        return this.lightCount;
    }

    public boolean valid() {
        return referencedSignalUUID != null;
    }

    public SwedishSignalAspect getCurrentDisplayingAspect() {
        if (referencedSignalUUID == null)
            return SwedishSignalAspect.SIGNAL_FAULT_INCORRECT_WIRING;

        RawSignalState rawSignalStateState = ExtendedSignalsCore.clientNetworkCache()
                .signalStates()
                .get(referencedSignalUUID);

        if (rawSignalStateState == null)
            return SwedishSignalAspect.STOP;

        return rawSignalStateState.isProceed() ? SwedishSignalAspect.PROCEED_80 : SwedishSignalAspect.STOP;
        // return connectedSignalBox.getCurrentAspect();
    }

    public SignalBlockEntity.SignalState getCurrentDisplayingState() {
        return SignalBlockEntity.SignalState.GREEN;
    }

    public boolean blink() {
        return tick > 10;
    }

    protected void computeSignalLightValues(SwedishSignalAspect aspect, SignalBlockEntity.SignalState createSignalState, boolean doInvalidBlinking) {
        if (doInvalidBlinking || aspect == null) {
            for (int i = 0; i < lightLevels.length; i++) {
                lightLevels[i] = blink() ? 1 : 0;
            }
            return;
        }

        for (int i = 0; i < lightCount; i++) {
            char s = aspect.getLightPattern().charAt(i);
            switch (s) {
                case 'S':
                    SignalUtilities.computeLightValueAt(i, lightLevels, true);
                    break;
                case 'F':
                    SignalUtilities.computeLightValueAt(i, lightLevels, blink());
                    break;
                case 'U':
                    SignalUtilities.computeLightValueAt(i, lightLevels, false);
                    break;
                default:
                    lightLevels[i] = blink() ? 1 : 0;
                    break;
            }
        }
    }

    /**
     * this method will be called when looking at a BlockEntity that implemented
     * this
     * interface
     *
     * @param tooltip
     * @param isPlayerSneaking
     * @return {@code true} if the tooltip creation was successful and should be
     * displayed, or {@code false} if the overlay should not be displayed
     */
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        Lang.builder(ExtendedSignalsSweden.MOD_ID).add(Component.literal("WIP")).forGoggles(tooltip);
        // SwedishSignalAspect signalAspect = this.getCurrentAspect();
        // if (signalAspect != null && this.valid()) {
        // 	Lang.builder().add(Component.translatable(signalAspect.getDescription())).forGoggles(tooltip);
        // }
        // return true;
        return true;
    }

    /**
     * Return an {@link AABB} that controls the visible scope of a
     * {@link BlockEntityWithoutLevelRenderer} associated with this
     * {@link BlockEntity}
     * Defaults to the collision bounding box
     * {@link BlockState#getCollisionShape(BlockGetter, BlockPos)} associated with
     * the block
     * at this location.
     *
     * @return an appropriately size {@link AABB} for the {@link BlockEntity}
     */
    @Override
    public AABB getRenderBoundingBox() {
        return AABB.ofSize(getBlockPos().getCenter(), 1, 2, 1);
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
                bindToSignal(sb.getSignal().id);
                return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully bound to signal box"));
            }
        }
        return ISignalTunerBindable.super.sourceBindingToReader(sourceBlockEntity, mode);
    }

    private void bindToSignal(UUID id) {
        this.referencedSignalUUID = id;
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
        this.referencedSignalUUID = tag.hasUUID(TAG_REFERENCED_SIGNAL_UUID) ? tag.getUUID(TAG_REFERENCED_SIGNAL_UUID) : null;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        referencedSignalUUID = pTag.hasUUID(TAG_REFERENCED_SIGNAL_UUID) ? pTag.getUUID(TAG_REFERENCED_SIGNAL_UUID) : null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (referencedSignalUUID == null) return;

        pTag.putUUID(TAG_REFERENCED_SIGNAL_UUID, referencedSignalUUID);
    }
}
