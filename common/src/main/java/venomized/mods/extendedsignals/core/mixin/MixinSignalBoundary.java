package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.ISignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.create.tracks.points.ITrainSpeedModifier;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundUpdateMainSignalEdgePointTagPacket;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.TrainHelp;

import java.util.List;
import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements ISignal<SignalBoundary>, ISignalStateEvaluator, ITrainSpeedModifier {
    @Unique
    public Couple<UUID> extendedSignals$nextSignalBoundaryEdgePointId;
    @Shadow
    public Couple<UUID> groups;
    @Unique
    private Couple<Boolean> extendedSignals$isMainSignal;

    @Shadow
    public abstract boolean isForcedRed(boolean primary);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void extendedSignals$Ctor(CallbackInfo ci) {
        extendedSignals$nextSignalBoundaryEdgePointId = Couple.create(() -> null);
        extendedSignals$isMainSignal = Couple.create(false, false);
        // extendedSignals$stateRemapperIDs = Couple.create(SignalStateRemapper.NONE.getId(), SignalStateRemapper.NONE.getId());
    }

    /**
     * @param upcomingSignal
     * @return
     */
    @Override
    public SignalStateNode evaluateSignalState(Direction.AxisDirection direction, SignalStateNode upcomingSignal, Train train) {
        boolean primary = direction == Direction.AxisDirection.POSITIVE;
        SignalEdgeGroup entering = Create.RAILWAYS.signalEdgeGroups.get(groups.get(primary));
        if (isForcedRed(primary))
            return SignalStateNode.STOP;
        if (InterlockingManager.trainOwnsGroupIntersecting(train, entering) == InterlockingManager.ReservationResult.CONFLICT)
            return SignalStateNode.STOP;

        return new SignalStateNode().setProceed(true).setReserved(true);
    }

    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
            at = @At(
                    value = "TAIL"
            )
    )
    public void extendedSignals$read(CompoundTag nbt, HolderLookup.Provider registries, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        for (boolean side : Iterate.trueAndFalse) {
            // TODO: Remove this NBT Migration data in the future
            if (nbt.contains("chaining" + (side ? "0" : "1"))) {
                extendedSignals$isMainSignal.set(
                        side, !nbt.getBoolean("chaining" + (side ? "0" : "1"))
                );
                continue;
            }
            extendedSignals$isMainSignal.set(
                    side, nbt.getBoolean("main_signal_capable" + (side ? "0" : "1"))
            );
        }
    }

    @Inject(method = "write(Lnet/minecraft/network/FriendlyByteBuf;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"))
    public void extendedSignals$networkWrite(FriendlyByteBuf buffer, DimensionPalette dimensions, CallbackInfo ci) {
        for (int i = 1; i <= 2; i++)
            buffer.writeBoolean(extendedSignals$isMainSignal.get(i == 1));
    }

    @Inject(method = "read(Lnet/minecraft/network/FriendlyByteBuf;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("TAIL"))
    public void extendedSignals$networkRead(FriendlyByteBuf buffer, DimensionPalette dimensions, CallbackInfo ci) {
        for (int i = 1; i <= 2; i++)
            extendedSignals$isMainSignal.set(i == 1, buffer.readBoolean());
    }


    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("HEAD"))
    public void extendedSignals$write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions, CallbackInfo ci) {
        for (boolean side : Iterate.trueAndFalse) {
            nbt.putBoolean("main_signal_capable" + (side ? "0" : "1"), extendedSignals$isMainSignal.get(side));
        }
    }


    @Inject(method = "setGroupAndUpdate", at = @At("TAIL"))
    public void extendedSignals$setGroupAndUpdateScoutForwardForNextBlock(TrackNode side, UUID groupId, CallbackInfo ci) {
        TrackGraph currentGraph = Create.RAILWAYS.getGraph(null, side.getLocation());
        if (currentGraph == null)
            return;
        Couple<TrackNode> nodes = edgeLocation.map(
                currentGraph::locateNode
        );
        TrackEdge connection = currentGraph.getConnection(nodes);
        TravellingPoint nextBlockSignalScout = new TravellingPoint(
                nodes.getSecond(),
                nodes.getFirst(),
                connection,
                position,
                false
        );

        boolean front = isPrimary(side);
        nextBlockSignalScout.travel(
                currentGraph,
                256 * (front ? 1 : -1),
                nextBlockSignalScout.steer(
                        TravellingPoint.SteerDirection.NONE,
                        new Vec3(0, 1, 0)
                ),
                (distance, detected) -> {
                    if (detected.getFirst() instanceof SignalBoundary signalBoundary) {
                        extendedSignals$nextSignalBoundaryEdgePointId.set(front, signalBoundary.getId());
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
     * @param points
     * @param train
     */
    @Override
    public void applySpeed(boolean front, List<CollectedEdgePoint> points, Train train) {
        if (isMainSignal(front))
            train.throttle = TrainHelp.trainSpeedPercentFromKph(currentSignalState(front).getMaxProceedSpeed(), train, train.manualTick);
    }
}
