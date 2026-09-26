package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.ISignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.create.tracks.points.ITrainSpeedModifier;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundUpdateMainSignalEdgePointTagPacket;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.TrainHelp;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Debug(export = true, print = true)
@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements ISignal<SignalBoundary>, ISignalStateEvaluator, ITrainSpeedModifier {
    /**
     * @return
     */
    @Override
    public Component getName() {
        return Component.translatable("train_map.extended_signals.edgepoint.create_signal.name");
    }

    @Unique
    private Couple<SignalStateNode> extendedSignals$blockSignalState;
    @Unique
    public Couple<UUID> extendedSignals$nextSignalBoundaryEdgePointId;
    @Unique
    public Couple<Boolean> extendedSignals$nextSignalBoundaryEdgePointFront;
    @Shadow
    public Couple<UUID> groups;
    @Unique
    private Couple<Boolean> extendedSignals$isMainSignal;
    @Unique
    private Couple<Boolean> extendedSignals$clientFacingDirections;
    @Unique
    private Couple<Boolean> extendedSignals$blockSignalModeEnabled;
    @Unique
    private Couple<SignalBlockEntity.SignalState> extendedSignals$previousSignalState;

    @Shadow
    public abstract boolean isForcedRed(boolean primary);

    @Shadow
    public Couple<Map<BlockPos, Boolean>> blockEntities;

    @Shadow
    public abstract boolean canNavigateVia(TrackNode side);

    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Shadow
    public Couple<Boolean> sidesToUpdate;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void extendedSignals$Ctor(CallbackInfo ci) {
        extendedSignals$isMainSignal = Couple.create(() -> false);
        extendedSignals$clientFacingDirections = Couple.create(() -> false);
        extendedSignals$nextSignalBoundaryEdgePointId = Couple.create(() -> null);
        extendedSignals$nextSignalBoundaryEdgePointFront = Couple.create(() -> false);
        extendedSignals$blockSignalState = Couple.create(SignalStateNode.INVALID(this, false), SignalStateNode.INVALID(this, false));
        extendedSignals$blockSignalModeEnabled = Couple.create(() -> false);
        extendedSignals$previousSignalState = Couple.create(() -> SignalBlockEntity.SignalState.INVALID);
        // extendedSignals$stateRemapperIDs = Couple.create(SignalStateRemapper.NONE.getId(), SignalStateRemapper.NONE.getId());
    }

    /**
     * @param primary
     * @param node
     * @return
     */
    @Override
    public boolean facingDirections(boolean primary, TrackNode node) {
        return extendedSignals$clientFacingDirections.get(primary);
    }



    /**
     * @param upcomingSignal
     * @return
     */
    @Override
    public SignalStateNode evaluateSignalState(Direction.AxisDirection direction, SignalStateNode upcomingSignal, Train train) {
        boolean primary = direction == Direction.AxisDirection.POSITIVE;
        if (extendedSignals$blockSignalModeEnabled.get(primary)) {
            return SignalStateNode.fromCache(this, primary);
        }
        SignalEdgeGroup entering = Create.RAILWAYS.signalEdgeGroups.get(groups.get(primary));
        if (isForcedRed(primary))
            return ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY.modifyState(id, primary, state -> state.setProceed(false));
        if (InterlockingManager.trainOwnsGroupIntersecting(train, entering) == InterlockingManager.ReservationResult.CONFLICT)
            return ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY.modifyState(id, primary, state -> state.setProceed(false));

        return ExtendedSignals.serverNetworkCache().modifyState(this.id, primary, state -> state.setProceed(true));
    }

    @Inject(method = "write(Lnet/minecraft/network/FriendlyByteBuf;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"))
    public void extendedSignals$networkWrite(FriendlyByteBuf buffer, DimensionPalette dimensions, CallbackInfo ci) {
        for (int i = 1; i <= 2; i++)
            buffer.writeBoolean(extendedSignals$isMainSignal.get(i == 1));
        for (int i = 1; i <= 2; i++)
            buffer.writeBoolean(blockEntities.get(i == 1).isEmpty());
    }

    @Inject(method = "read(Lnet/minecraft/network/FriendlyByteBuf;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"))
    public void extendedSignals$networkRead(FriendlyByteBuf buffer, DimensionPalette dimensions, CallbackInfo ci) {
        for (int i = 1; i <= 2; i++)
            extendedSignals$isMainSignal.set(i == 1, buffer.readBoolean());
        for (int i = 1; i <= 2; i++)
            extendedSignals$clientFacingDirections.set(i == 1, buffer.readBoolean());
    }


    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
            at = @At(
                    value = "HEAD"
            )
    )
    public void extendedSignals$read(CompoundTag nbt, HolderLookup.Provider registries, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        for (boolean side : Iterate.trueAndFalse) {
            // TODO: Remove this NBT Migration data in the future
            if (nbt.contains("chaining" + (side ? "0" : "1"))) {
                extendedSignals$isMainSignal.set(
                        side, !nbt.getBoolean("chaining" + (side ? "0" : "1"))
                );
            } else {
                extendedSignals$isMainSignal.set(
                        side, nbt.getBoolean("main_signal_capable" + (side ? "0" : "1"))
                );
            }

            if (nbt.contains("next_signal_id" + (side ? "0" : "1")))
                extendedSignals$nextSignalBoundaryEdgePointId.set(side, nbt.getUUID("next_signal_id" + (side ? "0" : "1")));
            extendedSignals$nextSignalBoundaryEdgePointFront.set(side, nbt.getBoolean("next_signal_front" + (side ? "0" : "1")));
            extendedSignals$blockSignalModeEnabled.set(side, nbt.getBoolean("block_signal_mode" + (side ? "0" : "1")));
        }
    }

    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("HEAD"))
    public void extendedSignals$write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions, CallbackInfo ci) {
        for (boolean side : Iterate.trueAndFalse) {
            nbt.putBoolean("main_signal_capable" + (side ? "0" : "1"), extendedSignals$isMainSignal.get(side));

            UUID next = extendedSignals$nextSignalBoundaryEdgePointId.get(side);
            if (next != null)
                nbt.putUUID("next_signal_id" + (side ? "0" : "1"), next);
            nbt.putBoolean("next_signal_front" + (side ? "0" : "1"), extendedSignals$nextSignalBoundaryEdgePointFront.get(side));

            nbt.putBoolean("block_signal_mode" + (side ? "0" : "1"), extendedSignals$blockSignalModeEnabled.get(side));
        }
    }

    @Unique
    private void extendedSignals$rescoutForNextSignal(TrackGraph graph, boolean front) {
        if (graph == null)
            return;
        Couple<TrackNode> nodes = edgeLocation.map(
                graph::locateNode
        );
        TrackEdge connection = graph.getConnection(nodes);
        TravellingPoint nextBlockSignalScout = new TravellingPoint(
                nodes.getFirst(),
                nodes.getSecond(),
                connection,
                position,
                false
        );

        nextBlockSignalScout.travel(
                graph,
                ExtendedSignalsConfig.SERVER.defaultScanDistance.getAsDouble(),
                nextBlockSignalScout.steer(
                        TravellingPoint.SteerDirection.NONE,
                        new Vec3(0, 1, 0)
                ),
                (distance, detected) -> {
                    if (detected.getFirst() instanceof SignalBoundary signalBoundary) {
                        if ((Object) this == signalBoundary)
                            return false;
                        extendedSignals$nextSignalBoundaryEdgePointId.set(front, signalBoundary.getId());
                        extendedSignals$nextSignalBoundaryEdgePointFront.set(front,
                                signalBoundary.isPrimary(detected.getSecond().getSecond()) == this.isPrimary(nodes.getSecond()));
                        return true;
                    }
                    return false;
                }
        );
    }

    /**
     * @return
     */
    @Override
    public boolean isMainSignal(boolean front) {
        return extendedSignals$isMainSignal.get(front);
    }

    /**
     * @param front
     * @param mainSignal
     */
    @Override
    public void setMainSignal(boolean front, boolean mainSignal) {
        if (extendedSignals$isMainSignal.get(front) == mainSignal) {
            return;
        }

        extendedSignals$isMainSignal.set(front, mainSignal);
        PacketDistributor.sendToAllPlayers(
                new ClientBoundUpdateMainSignalEdgePointTagPacket(
                        getType().getId(), getId(), front, mainSignal
                )
        );
    }

    /**
     * @param front
     * @param blockSignalMode
     */
    @Override
    public void setBlockSignalMode(boolean front, boolean blockSignalMode) {
        extendedSignals$blockSignalModeEnabled.set(front, blockSignalMode);
        sidesToUpdate.set(front, true);
    }

    /**
     * @param front
     * @return
     */
    @Override
    public boolean isBlockSignalMode(boolean front) {
        return extendedSignals$blockSignalModeEnabled.get(front);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/createmod/catnip/data/Couple;get(Z)Ljava/lang/Object;"))
    public void extendedSignals$tick(CallbackInfo ci, @Local(name = "graph") TrackGraph graph, @Local(name = "front") boolean front) {
        if (!isBlockSignalMode(front))
            return;
        if (!isMainSignal(front))
            return;
        // Create.RAILWAYS.signalEdgeGroups.get(groups.get(front)).isOccupiedUnless((SignalBoundary)(Object)this)
        extendedSignals$previousSignalState.set(front, cachedStates.get(front));
        extendedSignals$rescoutForNextSignal(graph, front);
        UUID point = extendedSignals$nextSignalBoundaryEdgePointId.get(front);
        boolean nextFront = extendedSignals$nextSignalBoundaryEdgePointFront.get(front);
        extendedSignals$blockSignalState.set(front, ExtendedSignals.serverNetworkCache().modifyState(this.id, front, state ->
                state.setNextState(SignalStateNode.fromCache(point, nextFront)).setProceed(cachedStates.get(front) != SignalBlockEntity.SignalState.RED)
        ));
    }

    /**
     * @param front
     * @param points
     * @param train
     */
    @Override
    public void applySpeed(boolean front, List<CollectedEdgePoint> points, Train train) {
        if (isMainSignal(front))
            train.throttle = TrainHelp.trainSpeedPercentFromKph(currentSignalState(front).getMaxProceedSpeed(), train, train.manualTick);
    }
}
