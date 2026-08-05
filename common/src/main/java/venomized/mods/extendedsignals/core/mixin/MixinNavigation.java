package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
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
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.create.tracks.*;
import venomized.mods.extendedsignals.core.create.tracks.points.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.signalling.ISignalStateBoundaryTransformer;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements INavigation {
    @Unique
    private static final int SIGNAL_SCOUT_INTERVAL = 10;
    @Unique
    private final Set<UUID> extendedSignals$ownedSignalGroups = new HashSet<>();
    @Unique
    private final ObjectArrayList<CollectedSignal> extendedSignals$collectedSignals = new ObjectArrayList<>();
    @Unique
    private final Map<ResourceLocation, ISignalModifier> extendedSignals$activeModifiers = new Object2ObjectLinkedOpenHashMap<>();
    @Unique
    private final Map<ResourceLocation, ISignalModifier> extendedSignals$predictedModifiers = new Object2ObjectLinkedOpenHashMap<>();
    @Shadow
    public Train train;
    @Shadow
    public double distanceToDestination;
    @Shadow
    public double distanceToSignal;
    @Shadow
    public Pair<UUID, Boolean> waitingForSignal;
    @Shadow
    private Map<UUID, Pair<SignalBoundary, Boolean>> waitingForChainedGroups;
    @Unique
    private long extendedSignals$signalScoutCooldown = 0;
    @Unique
    private TravellingPoint extendedSignals$signalScoutTriggerCollector;

    @Shadow
    public abstract TravellingPoint.ITrackSelector controlSignalScout();

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D")
    )
    public double extendedSignals$increaseReservationScanDistance(double pValue, double pMin, double pMax) {
        /* ORIGINAL:
        double brakingDistanceNoFlicker = brakingDistance + 3 - (brakingDistance % 3);
		double scanDistance = Mth.clamp(brakingDistanceNoFlicker, preDepartureLookAhead, distanceToDestination);
         */
        double reservationDistance = Math.max(pValue, ExtendedSignalsConfig.SERVER.defaultScanDistance.get());
        return Mth.clamp(reservationDistance, pMin, pMax);
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 4.5))
    public double extendedSignals$increaseMinScanDistance(double constant) {
        return ExtendedSignalsConfig.SERVER.defaultMinScanDistance.get();
    }

    // @ModifyReturnValue(method = "controlSignalScout", at = @At("RETURN"))
    // public TravellingPoint.ITrackSelector extenededSignals$signalScoutProxy(TravellingPoint.ITrackSelector original) {
    //     return (a, b) -> {
    //         Map.Entry<TrackNode, TrackEdge> result = original.apply(a, b);
    //         // System.out.println(result);
    //         return result;
    //     };
    // }

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

    // @Inject(
    //         method = "lambda$tick$0",
    //         at = @At(
    //                 value = "INVOKE",
    //                 target = "Lcom/simibubi/create/content/trains/entity/Navigation;reserveChain()V"
    //         )
    // )
    // public void extendedSignals$attemptReservationFailIfConflict(MutableObject<Pair<UUID, Boolean>> trackingCrossSignal, double scanDistance, MutableDouble crossSignalDistanceTracker, double brakingDistanceNoFlicker, Double distance, Pair<?,?> couple, CallbackInfoReturnable<Boolean> cir) {
    //     InterlockingManager.tryReserveChain(train, waitingForChainedGroups.keySet());
    // }
    @Inject(method = "reserveChain", at = @At("HEAD"))
    public void extendedSignals$persistChainReservation(CallbackInfo ci) {
        InterlockingManager.tryReserveChain(train, waitingForChainedGroups);
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
        extendedSignals$signalScoutTriggerCollector.travel(
                train.graph,
                (lookAheadDistance + 5) * speedMod,
                controlSignalScout(),
                (distance, trackEdgePointCouplePair) -> {
                    TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();

                    boolean primary = trackEdgePoint.isPrimary(trackEdgePointCouplePair.getSecond()
                            .getSecond()
                    );

                    if (!(trackEdgePoint instanceof IExtendedSignalBoundary<?> extendedSignalPoint))
                        return false;

                    if (trackEdgePoint instanceof TrackEdgePointSignalModifier<?> modifierPoint && modifierPoint.isAligned(primary)) {
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


                    boolean waiting = extendedSignals$isSignalWaiting(trackEdgePoint, primary);
                    extendedSignals$collectedSignals.push(
                            new CollectedSignal(
                                    extendedSignalPoint,
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

    @Unique
    private boolean extendedSignals$isSignalWaiting(TrackEdgePoint trackEdgePoint, boolean primary) {
        boolean blockOccupied = false;
        if (trackEdgePoint instanceof SignalBoundary createSignalBoundary) {
            UUID entering = createSignalBoundary.groups.get(primary);
            SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get(entering);
            if (signalEdgeGroup != null)
                blockOccupied = (!signalEdgeGroup.trains.contains(train) && !signalEdgeGroup.trains.isEmpty()) ||
                        createSignalBoundary.isForcedRed(primary) ||
                        InterlockingManager.trainOwnsGroupIntersecting(train, signalEdgeGroup) == InterlockingManager.ReservationResult.CONFLICT;
        }
        return blockOccupied || ((waitingForSignal != null) && trackEdgePoint.id.equals(waitingForSignal.getFirst())) && primary == waitingForSignal.getSecond();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/simibubi/create/content/trains/entity/Navigation;waitingForSignal:Lnet/createmod/catnip/data/Pair;",
                    ordinal = 6,
                    opcode = Opcodes.GETFIELD
            )
    )
    private void extendedSignals$updateDistantReservations(Level level, CallbackInfo ci) {
        if (train.graph == null)
            return;
        if (waitingForSignal != null)
            return;
//
        InterlockingManager.groupOwnerships.entrySet().stream().filter(e -> e.getValue().train().equals(train.id))
                .forEach(entry -> {
                    SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get(entry.getKey());
                    if (signalEdgeGroup == null)
                        return;
//
                    if (signalEdgeGroup.reserved == null)
                        signalEdgeGroup.reserved = entry.getValue().boundary();
                });
    }

    @Inject(method = "cancelNavigation", at = @At("RETURN"))
    public void extendedSignals$navigationCancelled(CallbackInfo ci) {
        InterlockingManager.clearReservationsForTrain(train);
    }

    // @ModifyExpressionValue(method = "lambda$tick$0", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/signal/SignalEdgeGroup;isOccupiedUnless(Lcom/simibubi/create/content/trains/entity/Train;)Z"))
    // public boolean extendedSignals$includeSignalOwnershipInOccupancyCheck(boolean original, @Local(name = "signalEdgeGroup") SignalEdgeGroup signalEdgeGroup) {
    //     return original || InterlockingManager.trainOwnsGroupIntersecting(train, signalEdgeGroup) ==
    //                     InterlockingManager.ReservationResult.CONFLICT;
    // }

    // @Inject(method = "currentSignalResolved", at = @At("HEAD"), cancellable = true)
    // public void extendedSignals$includeLockReservation(CallbackInfoReturnable<Boolean> cir) {
    //     if (InterlockingManager.isWaitingSignalBlockedByReservation(train, waitingForSignal, waitingForChainedGroups)) {
    //         cir.setReturnValue(false);
    //         cir.cancel();
    //     }
    // }

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
