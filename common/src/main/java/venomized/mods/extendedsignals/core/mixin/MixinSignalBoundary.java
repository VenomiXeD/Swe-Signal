package venomized.mods.extendedsignals.core.mixin;

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
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.create.tracks.*;
import venomized.mods.extendedsignals.core.signalling.ISignalStateBoundaryTransformer;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.Objects;
import java.util.UUID;

@Mixin(value = SignalBoundary.class, remap = false)
public abstract class MixinSignalBoundary extends TrackEdgePoint implements IExtendedEdgePoint<SignalBoundary>, ISignalStateCompute, ISignalStateBoundaryTransformer {
    private static final String TAG_MAPPER_NAME = "mapper";
    private static final String TAG_SKIP_CHAIN_CONFIG_NAME = "chaining";

    @Unique
    public Couple<UUID> extendedSignals$nextSignalBoundaryEdgePointId;

    @Shadow
    public Couple<SignalBlockEntity.SignalState> cachedStates;

    @Shadow
    public Couple<UUID> groups;
    @Shadow
    public Couple<Boolean> sidesToUpdate;
    @Unique
    private Couple<ResourceLocation> extendedSignals$stateRemapperIDs;
    @Unique
    private Couple<Boolean> extendedSignals$skipChainingConfiguration;

    @Shadow
    public abstract void invalidate(LevelAccessor level);

    @Shadow
    public abstract boolean isForcedRed(boolean primary);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void extendedSignals$Ctor(CallbackInfo ci) {
        extendedSignals$nextSignalBoundaryEdgePointId = Couple.create(() -> null);
        extendedSignals$skipChainingConfiguration = Couple.create(false, false);
        extendedSignals$stateRemapperIDs = Couple.create(SignalStateRemapper.NONE.getId(), SignalStateRemapper.NONE.getId());
    }

    /**
     * @param upcomingSignal
     * @return
     */
    @Override
    public SignalStateNode computeSignalState(Direction.AxisDirection direction, SignalStateNode upcomingSignal, Train train) {
        boolean primary = direction == Direction.AxisDirection.POSITIVE;
        SignalEdgeGroup entering = Create.RAILWAYS.signalEdgeGroups.get(groups.get(primary));

        if (isForcedRed(primary))
            return SignalStateNode.STOP;

        if (InterlockingManager.trainOwnsGroupIntersecting(train, entering) == InterlockingManager.ReservationResult.CONFLICT)
            return SignalStateNode.STOP;

        // if (entering.isOccupiedUnless((SignalBoundary) (Object) this) &&
        //                 entering.isOccupiedUnless(train)) {
        //     return SignalStateNode.STOP;
        // }

        return new SignalStateNode().setProceed(true).setReserved(true);
    }

    /**
     * @param front
     * @param mapper
     */
    @Override
    public void setMapper(boolean front, SignalStateRemapper mapper) {
        extendedSignals$stateRemapperIDs.set(front, Objects.requireNonNullElse(mapper, SignalStateRemapper.NONE).getId());
    }

    @Override
    public SignalStateNode transformSignalState(Direction.AxisDirection direction, SignalStateNode state) {
        ResourceLocation mapperId = extendedSignals$stateRemapperIDs.get(direction == Direction.AxisDirection.POSITIVE);
        return SignalStateRemapper.MAPPERS.getOrDefault(mapperId, SignalStateRemapper.NONE).remap(state);
    }

    @Inject(method = "read(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;ZLcom/simibubi/create/content/trains/graph/DimensionPalette;)V",
            at = @At(
                    value = "TAIL"
            )
    )
    public void extendedSignals$read(CompoundTag nbt, HolderLookup.Provider registries, boolean migration, DimensionPalette dimensions, CallbackInfo ci) {
        extendedSignals$stateRemapperIDs.setFirst(NBTHelper.readResourceLocation(nbt, TAG_MAPPER_NAME + "0"));
        extendedSignals$stateRemapperIDs.setSecond(NBTHelper.readResourceLocation(nbt, TAG_MAPPER_NAME + "1"));
        for (boolean side : Iterate.trueAndFalse) {
            extendedSignals$skipChainingConfiguration.set(
                    side, nbt.getBoolean(TAG_SKIP_CHAIN_CONFIG_NAME + (side ? "0" : "1"))
            );
        }
    }


    @Inject(method = "write(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;Lcom/simibubi/create/content/trains/graph/DimensionPalette;)V", at = @At("HEAD"))
    public void extendedSignals$write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions, CallbackInfo ci) {
        NBTHelper.writeResourceLocation(nbt, TAG_MAPPER_NAME + "0", extendedSignals$stateRemapperIDs.get(false));
        NBTHelper.writeResourceLocation(nbt, TAG_MAPPER_NAME + "1", extendedSignals$stateRemapperIDs.get(true));

        for (boolean side : Iterate.trueAndFalse) {
            nbt.putBoolean(TAG_SKIP_CHAIN_CONFIG_NAME + (side ? "0" : "1"), extendedSignals$skipChainingConfiguration.get(side));
        }
    }

    /**
     * @return
     */
    @Override
    public boolean avoidSignalChaining(Direction.AxisDirection direction, Train train) {
        return extendedSignals$skipChainingConfiguration.get(direction == Direction.AxisDirection.POSITIVE);
    }

    /**
     * @param front
     * @param skipChaining
     */
    @Override
    public void setChainingSkipped(boolean front, boolean skipChaining) {
        extendedSignals$skipChainingConfiguration.set(front, skipChaining);
    }

    /**
     * @param front
     * @return
     */
    @Override
    public boolean getChainingSkipped(boolean front) {
        return extendedSignals$skipChainingConfiguration.get(front);
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
    public UUID pointId() {
        return getId();
    }
}
