package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.create.tracks.*;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;
import venomized.mods.extendedsignals.core.signalling.ISignalStateBoundaryTransformer;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.*;
import java.util.function.BiConsumer;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements INavigation {
    @Shadow
    public abstract TravellingPoint.ITrackSelector controlSignalScout();

    @Shadow
    public Train train;
    @Shadow
    public double distanceToDestination;
    @Shadow
    public double distanceToSignal;
    @Shadow
    public Pair<UUID, Boolean> waitingForSignal;
    @Unique
    private final Set<UUID> extendedSignals$ownedSignalGroups = new HashSet<>();
    @Unique
    private static final int SIGNAL_SCOUT_INTERVAL = 10;
    @Unique
    private long extendedSignals$signalScoutCooldown = 0;
    @Unique
    private final ObjectArrayList<CollectedSignal> extendedSignals$collectedSignals = new ObjectArrayList<>();
    @Unique
    private final Map<ResourceLocation, ISignalModifier> extendedSignals$activeModifiers = new Object2ObjectLinkedOpenHashMap<>();
    @Unique
    private final Map<ResourceLocation, ISignalModifier> extendedSignals$predictedModifiers = new Object2ObjectLinkedOpenHashMap<>();
    @Unique
    private TravellingPoint extendedSignals$signalScoutTriggerCollector;

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D")
    )
    public double extendedSignals$increaseReservationScanDistance(double pValue, double pMin, double pMax) {
        double reservationDistance = Mth.absMax(pValue, ExtendedSignalsConfig.SERVER.defaultScanDistance.get());
        return Mth.clamp(reservationDistance, pMin, pMax);
    }

    @ModifyReturnValue(method = "controlSignalScout", at = @At("RETURN"))
    public TravellingPoint.ITrackSelector extenededSignals$signalScoutProxy(TravellingPoint.ITrackSelector original) {
        return (a, b) -> {
            Map.Entry<TrackNode, TrackEdge> result = original.apply(a, b);
            // System.out.println(result);
            return result;
        };
    }

    @Inject(method = "startNavigation", at = @At("HEAD"))
    public void extendedSignals$onStartNavigation(DiscoveredPath pathTo, CallbackInfoReturnable<Double> cir) {
        extendedSignals$activeModifiers.clear();

        // Force scout on departure
        extendedSignals$signalScoutCooldown = 0;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void extendedSignals$onCtor(Train train, CallbackInfo ci) {
        extendedSignals$signalScoutTriggerCollector = new TravellingPoint();
    }

    // @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    // public double modify(double original) {
    //     return Math.min(
    //             LOOK_AHEAD_DISTANCE,
    //             Math.min(distanceToDestination, distanceToSignal)
    //     );
    // }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/TravellingPoint;travel(Lcom/simibubi/create/content/trains/graph/TrackGraph;DLcom/simibubi/create/content/trains/entity/TravellingPoint$ITrackSelector;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IEdgePointListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$ITurnListener;)D",
                    shift = At.Shift.AFTER
            )
    )
    public void extendedSignals$tick(Level level,
                                     CallbackInfo ci,
                                     @Local(name = "speedMod") double speedMod,
                                     @Local(name = "leadingPoint") TravellingPoint leadingPoint
    ) {
        if (extendedSignals$signalScoutCooldown-- > 0)
            return;
        extendedSignals$signalScoutCooldown = SIGNAL_SCOUT_INTERVAL;

        extendedSignals$signalScoutCooldown = extendedSignals$signalScoutCooldown < 0 ? 20 : extendedSignals$signalScoutCooldown - 1;

        extendedSignals$signalScoutTriggerCollector.node1 = leadingPoint.node1;
        extendedSignals$signalScoutTriggerCollector.node2 = leadingPoint.node2;
        extendedSignals$signalScoutTriggerCollector.edge = leadingPoint.edge;
        extendedSignals$signalScoutTriggerCollector.position = leadingPoint.position;


        extendedSignals$collectSignalsInPath(speedMod);
        extendedSignals$resolveSignallingLogic();
    }

    /**
     * @return
     */
    @Override
    public Map<ResourceLocation, ISignalModifier> extendedSignals$activeModifiers() {
        return this.extendedSignals$activeModifiers;
    }

    @Unique
    private void extendedSignals$collectSignalsInPath(double speedMod) {
        final double lookAheadDistance = Math.min(
                ExtendedSignalsConfig.SERVER.defaultScanDistance.get(),
                Math.min(distanceToDestination, distanceToSignal)
        );

        extendedSignals$collectedSignals.clear();
        extendedSignals$predictedModifiers.clear();

        extendedSignals$predictedModifiers.putAll(
                extendedSignals$activeModifiers
        );

        final MutableDouble previousSignalDistance = new MutableDouble(-1);
        final Vec3[] previousTrackVector = {null};
        extendedSignals$signalScoutTriggerCollector.travel(
                train.graph,
                (lookAheadDistance + 5) * speedMod,
                controlSignalScout(),
                (distance, trackEdgePointCouplePair) -> {
                    TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();

                    if (!(trackEdgePoint instanceof IExtendedSignalBoundary<?> signalBoundary))
                        return false;

                    boolean primary = trackEdgePoint.isPrimary(trackEdgePointCouplePair.getSecond()
                            .getSecond()
                    );

                    if (signalBoundary instanceof TrackEdgePointSignalModifier<?> modifierPoint && modifierPoint.isAligned(primary)) {
                        if (modifierPoint.shouldApply()) {
                            extendedSignals$predictedModifiers
                                    .put(trackEdgePoint.getType().getId(), modifierPoint);
                        } else {
                            extendedSignals$predictedModifiers
                                    .remove(trackEdgePoint.getType().getId());
                        }

                        return false;
                    }

                    double deltaSignalDistance = previousSignalDistance.getValue() < 0
                            ? -1.0 : distance - previousSignalDistance.getValue();
                    previousSignalDistance.setValue(distance);

                    boolean waiting = waitingForSignal != null &&
                            Objects.equals(waitingForSignal.getFirst(), signalBoundary.pointId());

                    extendedSignals$collectedSignals.push(
                            new CollectedSignal(
                                    signalBoundary,
                                    primary ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                    waiting,
                                    distance,
                                    deltaSignalDistance,
                                    extendedSignals$predictedModifiers.values().toArray(ISignalModifier[]::new))
                    );
                    return waiting;
                }
        );
    }


    @Inject(method = "cancelNavigation", at = @At("RETURN"))
    public void extendedSignals$navigationCancelled(CallbackInfo ci) {
        extendedSignals$flushReservations();
    }

    @Unique
    private void extendedSignals$flushReservations() {
        Create.RAILWAYS.signalEdgeGroups.values().forEach(g -> {
            if (((ISignalEdgeGroup) g).extendedSignals$reservedByTrain() == train.id) {
                ((ISignalEdgeGroup) g).extendedSignals$setReservedByTrain(null);
            }
        });
        extendedSignals$ownedReservedSignals().clear();
    }

    // @Redirect(method = "lambda$tick$0", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/trains/signal/SignalEdgeGroup;reserved:Lcom/simibubi/create/content/trains/signal/SignalBoundary;", opcode = Opcodes.PUTFIELD))
    // private void extendedSignals$setSignalOwnership(SignalEdgeGroup instance, SignalBoundary value) {
    //     instance.reserved = value;
    //     ((ISignalEdgeGroup)instance).extendedSignals$setReservedByTrain(train);
//
    //     this.extendedSignals$ownedSignalGroups.add(instance.id);
    // }
//
    @WrapOperation(method = "reserveChain", at = @At(value = "INVOKE", target = "Ljava/util/Map;forEach(Ljava/util/function/BiConsumer;)V"))
    private void extendedSignals$setSignalOwnershipWhenReservingChain(
            Map<UUID, Pair<SignalBoundary, Boolean>> instance,
            BiConsumer<UUID, Pair<SignalBoundary, Boolean>> action,
            Operation<Void> original
    ) {

        // First pass: check conflict
        for (UUID groupId : instance.keySet()) {
            SignalEdgeGroup group = Create.RAILWAYS.signalEdgeGroups.get(groupId);

            if (group == null)
                continue;

            if (extendedSignals$isReservedByOtherTrainOrIntersecting(group)) {
                // Another train already owns part of this chain.
                // Do NOT overwrite.
                return;
            }
        }

        // Second pass: reserve atomically
        instance.forEach((groupId, boundary) -> {
            SignalEdgeGroup group = Create.RAILWAYS.signalEdgeGroups.get(groupId);

            if (group != null) {
                group.reserved = boundary.getFirst();

                ((ISignalEdgeGroup) group)
                        .extendedSignals$setReservedByTrain(this.train);

                this.extendedSignals$ownedSignalGroups.add(group.id);
            }
        });
    }

    @ModifyExpressionValue(method = "lambda$tick$0", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/signal/SignalEdgeGroup;isOccupiedUnless(Lcom/simibubi/create/content/trains/entity/Train;)Z"))
    public boolean extendedSignals$includeSignalOwnershipInOccupancyCheck(boolean original, @Local(name = "signalEdgeGroup") SignalEdgeGroup signalEdgeGroup) {
        return original || extendedSignals$isReservedByOtherTrainOrIntersecting(signalEdgeGroup);
    }

    @Unique
    private boolean extendedSignals$isReservedByOtherTrain(SignalEdgeGroup group) {
        UUID reservedBy = ((ISignalEdgeGroup) group)
                .extendedSignals$reservedByTrain();

        return reservedBy != null && !reservedBy.equals(this.train.id);
    }

    @Unique
    private boolean extendedSignals$isReservedByOtherTrainOrIntersecting(SignalEdgeGroup group) {
        if (extendedSignals$isReservedByOtherTrain(group)) {
            return true;
        }

        if (group.intersectingResolved.isEmpty()) {
            ((MixinSignalEdgeGroupAccessor) group).extendedSignals$walkIntersecting(group.intersectingResolved::add);
        }

        for (SignalEdgeGroup intersecting : group.intersectingResolved) {
            if (extendedSignals$isReservedByOtherTrain(intersecting)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return
     */
    @Override
    public Set<UUID> extendedSignals$ownedReservedSignals() {
        return extendedSignals$ownedSignalGroups;
    }

    @Unique
    private void extendedSignals$resolveSignallingLogic() {
        SignalStateNode upcomingSignalState = null;
        SignalStateNode currentSignalState = SignalStateNode.INVALID;

        while (!extendedSignals$collectedSignals.isEmpty()) {
            CollectedSignal current = extendedSignals$collectedSignals.pop();

            if (current.boundary() instanceof ISignalStateCompute signalStateEvaluator) {
                currentSignalState = signalStateEvaluator.computeSignalState(
                                current.signalDirection(),
                                upcomingSignalState,
                                train
                        )
                        .setNextState(upcomingSignalState)
                        .setAxisDirection(current.signalDirection())
                        .setDistanceToNextSignal(current.distanceFromPreviousSignal());

                if (signalStateEvaluator instanceof ISignalStateBoundaryTransformer transformer) {
                    currentSignalState = transformer.transformSignalState(current.signalDirection(), currentSignalState);
                }

                if (current.isStoppingAtThisNode())
                    currentSignalState.setProceed(false);

                // if (current.boundary() instanceof SignalBoundary sb) {
                //     train.reservedSignalBlocks.add(sb.getId());
                // }
            }

            for (ISignalModifier modifier : current.signalModifierSnapshot()) {
                modifier.applyModifier(currentSignalState);
            }

            current.boundary().onSignalScout(
                    current.signalDirection(), currentSignalState, this.train, current.distance()
            );

            if (!current.boundary().doSkipChaining(current.signalDirection(), train))
                upcomingSignalState = currentSignalState;
        }
    }
}
