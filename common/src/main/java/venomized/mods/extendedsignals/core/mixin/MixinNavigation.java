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
import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.joml.Math;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.EncounteredPoint;
import venomized.mods.extendedsignals.core.create.tracks.ISignalStateEvaluator;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignalStateModifier;
import venomized.mods.extendedsignals.core.create.tracks.points.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements INavigation {
    @Unique
    private static final int SIGNAL_SCOUT_INTERVAL = 10;
    @Unique
    private final ReferenceArrayList<CollectedEdgePoint> extendedSignals$persistentCollectedEdgePoints = new ReferenceArrayList<>();
    @Unique
    private final ReferenceArrayList<CollectedEdgePoint> extendedSignals$collectedEdgePoints = new ReferenceArrayList<>();
    @Unique
    private final Map<ResourceLocation, EncounteredPoint> extendedSignals$activeModifiers = new Object2ReferenceLinkedOpenHashMap<>();
    // @Unique
    // private final Map<ResourceLocation, ISignalModifier> extendedSignals$encounteredModifiers = new Object2ReferenceLinkedOpenHashMap<>();
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

    /**
     * @param cooldown
     */
    @Override
    public void extendedSignals$setSignalScoutCooldown(int cooldown) {
        this.extendedSignals$signalScoutCooldown = cooldown;
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
        // extendedSignals$activeModifiers.clear();

        // Force scout on departure
        extendedSignals$signalScoutCooldown = 0;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void extendedSignals$onCtor(Train train, CallbackInfo ci) {
        extendedSignals$signalScoutTriggerCollector = new TravellingPoint();
    }

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
        if (extendedSignals$signalScoutCooldown-- > 0 && train.speed != 0)
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
    public Map<ResourceLocation, EncounteredPoint> extendedSignals$encounteredTrackEdgePointModifiers() {
        return this.extendedSignals$activeModifiers;
    }

    /**
     * @return
     */
    @Override
    public List<CollectedEdgePoint> extendedSignals$currentScoutedEdgePoints() {
        return extendedSignals$persistentCollectedEdgePoints;
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

        extendedSignals$collectedEdgePoints.clear();

        final MutableDouble previousSignalDistance = new MutableDouble(-1);
        extendedSignals$signalScoutTriggerCollector.travel(
                train.graph,
                (lookAheadDistance + 1f) * speedMod,
                controlSignalScout(),
                (distance, trackEdgePointCouplePair) -> {
                    TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();
                    boolean front = trackEdgePoint.isPrimary(trackEdgePointCouplePair.getSecond()
                            .getSecond()
                    );

                    if (!(trackEdgePoint instanceof IExtendedEdgePoint<?> extendedSignalPoint)) {
                        return false;
                    }
                    double deltaSignalDistance = previousSignalDistance.getValue() < 0
                            ? -1.0 : distance - previousSignalDistance.getValue();
                    previousSignalDistance.setValue(distance);


                    boolean waiting = extendedSignals$isSignalWaiting(trackEdgePoint, front);
                    extendedSignals$collectedEdgePoints.push(
                            new CollectedEdgePoint(
                                    extendedSignalPoint,
                                    front ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                    waiting,
                                    distance,
                                    deltaSignalDistance,
                                    new ReferenceArrayList<>()
                            )
                    );
                    return waiting;
                }
        );
        extendedSignals$persistentCollectedEdgePoints.clear();
        extendedSignals$persistentCollectedEdgePoints.addAll(extendedSignals$collectedEdgePoints);

        final Map<ResourceLocation, ISignalStateModifier> predictedModifiers = new Object2ReferenceArrayMap<>();
        extendedSignals$encounteredTrackEdgePointModifiers().forEach((key, value) -> {
            ISignalStateModifier.ModifierAction action =
                    value.modifier().onAction(value.front(), extendedSignals$persistentCollectedEdgePoints, train);

            if (action == ISignalStateModifier.ModifierAction.APPLY)
                predictedModifiers.put(key, value.modifier());
            else if (action == ISignalStateModifier.ModifierAction.DISCARD)
                predictedModifiers.remove(key);
        });

        extendedSignals$collectedEdgePoints.forEach(collected -> {
            if (collected.boundary() instanceof TrackEdgePointSignalModifier<?> modifierPoint) {
                ISignalStateModifier.ModifierAction modifierAction = modifierPoint.onAction(
                        collected.signalDirection() == Direction.AxisDirection.POSITIVE,
                        extendedSignals$persistentCollectedEdgePoints, train);
                if (modifierAction == null) {
                    ExtendedSignals.LOGGER.info("A Signal modifier returned *null* as an action. This is undefined behavior: {}", modifierPoint.getClass().getName());
                    return;
                }

                switch (modifierAction) {
                    case APPLY -> predictedModifiers
                            .put(modifierPoint.getType().getId(), modifierPoint);
                    case DISCARD -> predictedModifiers
                            .remove(modifierPoint.getType().getId());
                }
            }
            collected.signalModifierSnapshot().addAll(predictedModifiers.values());
        });

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

    @Unique
    private void extendedSignals$resolveSignallingLogic() {
        SignalStateNode upcomingSignalState = null;
        SignalStateNode currentSignalState = null;

        while (!extendedSignals$collectedEdgePoints.isEmpty()) {
            CollectedEdgePoint current = extendedSignals$collectedEdgePoints.pop();
            TrackEdgePoint edgePoint = (TrackEdgePoint) current.boundary();

            if (edgePoint instanceof ISignal<?>) {
                if (edgePoint instanceof ISignalStateEvaluator evaluator)
                    currentSignalState = evaluator.evaluateSignalState(current.signalDirection(), upcomingSignalState, train);
                else
                    currentSignalState = SignalStateNode.fromCache(edgePoint, current.signalDirection() == Direction.AxisDirection.POSITIVE);
                currentSignalState.setDistanceToNextSignal(current.distanceFromPreviousSignal())
                        .setNextState(upcomingSignalState)
                        .setProceed(!current.isStoppingAtThisNode())
                        .setReservedBy(train.id);
            }

            if (currentSignalState != null) {
                for (ISignalStateModifier modifier : current.signalModifierSnapshot()) {
                    modifier.applyModifier(currentSignalState);
                }
            }

            current.boundary().onSignalScout(
                    current.signalDirection(), currentSignalState, this.train, current.distance()
            );

            if (current.boundary() instanceof ISignal<?> signalPoint && signalPoint.isMainSignal(current.signalDirection() == Direction.AxisDirection.POSITIVE)) {
                upcomingSignalState = currentSignalState;
            }
        }
    }
}
