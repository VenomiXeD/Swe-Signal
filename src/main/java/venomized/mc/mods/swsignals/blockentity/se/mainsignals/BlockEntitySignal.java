package venomized.mc.mods.swsignals.blockentity.se.mainsignals;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import it.unimi.dsi.fastutil.Pair;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
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
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntity;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignalBox;
import venomized.mc.mods.swsignals.core.SwSignal;
import venomized.mc.mods.swsignals.rail.se.SignalUtilities;
import venomized.mc.mods.swsignals.rail.se.SwedishSignalAspect;

import java.util.List;
import java.util.Optional;

public abstract class BlockEntitySignal extends SwBlockEntity
        implements IHaveGoggleInformation, ISignalTunerBindable {
    private static final String SIGNAL_BOX_POS_TAG = "signal_box_pos";
    private final int lightCount;
    public float[] lightLevels;
    private BlockPos signalBoxPosition = null;
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

    public void setTargetedSignalBoxPosition(BlockPos signalBoxPosition) {
        this.signalBoxPosition = signalBoxPosition;
        this.setChanged();
        this.updateSelf();
    }

    private BlockEntitySignalBox getConnectedSignalBox() {
        if (signalBoxPosition == null) {
            return null;
        }
        BlockEntity blockEntity = this.getLevel().getBlockEntity(signalBoxPosition);
        if (blockEntity instanceof BlockEntitySignalBox besb) {
            return besb;
        }
        return null;
    }

    public SwedishSignalAspect getCurrentDisplayingAspect() {
        BlockEntitySignalBox connectedSignalBox = this.getConnectedSignalBox();
        if (connectedSignalBox == null) {
            return null;
        }
        return SwedishSignalAspect.STOP;
        // return connectedSignalBox.getCurrentAspect();
    }

    public SignalBlockEntity.SignalState getCurrentDisplayingState() {
        return SignalBlockEntity.SignalState.GREEN;
        // BlockEntitySignalBox connectedSignalBox = this.getConnectedSignalBox();
        // if (connectedSignalBox == null) {
        //     return SignalBlockEntity.SignalState.INVALID;
        // }
        // return connectedSignalBox.getCreateSignalState();
    }

    public boolean valid() {
        return this.getConnectedSignalBox() != null;
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
        Lang.builder(SwSignal.MOD_ID).add(Component.literal("WIP")).forGoggles(tooltip);
        // SwedishSignalAspect signalAspect = this.getCurrentAspect();
        // if (signalAspect != null && this.valid()) {
        // 	Lang.builder().add(Component.translatable(signalAspect.getDescription())).forGoggles(tooltip);
        // }
        // return true;
        return true;
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
    public Pair<InteractionResult, MutableComponent> onBindToSource(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
        if (sourceBlockEntity.isPresent()) {
            if (sourceBlockEntity.get() instanceof BlockEntitySignalBox sb) {
                this.setTargetedSignalBoxPosition(sb.getBlockPos());
                return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully bound to signal box"));
            }
        }
        return ISignalTunerBindable.super.onBindToSource(sourceBlockEntity, mode);
    }

    @Override
    public boolean isSource() {
        return false;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag syncTag = super.getUpdateTag(registries);
        this.saveAdditional(syncTag, registries);
        return syncTag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        NbtUtils.readBlockPos(tag, SIGNAL_BOX_POS_TAG).ifPresent(pos -> signalBoxPosition = pos);
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.loadAdditional(pTag, registries);
        NbtUtils.readBlockPos(pTag, SIGNAL_BOX_POS_TAG).ifPresent(pos -> signalBoxPosition = pos);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider registries) {
        super.saveAdditional(pTag, registries);
        if (signalBoxPosition != null) {
            pTag.put(SIGNAL_BOX_POS_TAG, NbtUtils.writeBlockPos(signalBoxPosition));
        } else {
            pTag.putBoolean(SIGNAL_BOX_POS_TAG + "_missing", true);
        }
    }
}
