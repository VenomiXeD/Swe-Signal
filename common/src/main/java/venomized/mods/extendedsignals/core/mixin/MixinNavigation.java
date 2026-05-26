package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.create.tracks.*;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigationAccessor;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements INavigationAccessor {
    @Unique
    private static final double LOOK_AHEAD_DISTANCE = 256;
    @Unique
    private final Deque<CollectedSignal> extendedSignals$collectedSignals = new ArrayDeque<>();

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
    @Unique
    private TravellingPoint extendedSignals$signalScoutTriggerCollector;
    @Unique
    private int extendedSignals$randomTickValueForTesting = 20;

    @Shadow
    public abstract TravellingPoint.ITrackSelector controlSignalScout();

    @Inject(method = "startNavigation", at = @At("HEAD"))
    public void extendedSignals$onStartNavigation(DiscoveredPath pathTo, CallbackInfoReturnable<Double> cir) {
        extendedSignals$activeModifiers.clear();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void extendedSignals$onCtor(Train train, CallbackInfo ci) {
        extendedSignals$signalScoutTriggerCollector = new TravellingPoint();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/TravellingPoint;travel(Lcom/simibubi/create/content/trains/graph/TrackGraph;DLcom/simibubi/create/content/trains/entity/TravellingPoint$ITrackSelector;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IEdgePointListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$ITurnListener;)D"
            )
    )
    public void extendedSignals$tick(Level level,
                                     CallbackInfo ci,
                                     @Local(name = "speedMod") double speedMod,
                                     @Local(name = "leadingPoint") TravellingPoint leadingPoint
    ) {
        extendedSignals$randomTickValueForTesting = extendedSignals$randomTickValueForTesting < 0 ? 20 : extendedSignals$randomTickValueForTesting - 1;

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
                LOOK_AHEAD_DISTANCE,
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
                lookAheadDistance * speedMod,
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

                    extendedSignals$collectedSignals.push(
                            new CollectedSignal(
                                    signalBoundary,
                                    primary ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                    distance,
                                    deltaSignalDistance,
                                    extendedSignals$predictedModifiers.values().toArray(ISignalModifier[]::new)
                            )
                    );

                    if (signalBoundary instanceof SignalBoundary createTrueSignalBoundary)
                        return createTrueSignalBoundary.cachedStates.get(primary) == SignalBlockEntity.SignalState.RED;

                    return false;
                }
        );
    }

    @Unique
    private void extendedSignals$resolveSignallingLogic() {
        RawSignalState upcomingSignalState = null;
        RawSignalState currentSignalState = RawSignalState.INVALID;

        while (!extendedSignals$collectedSignals.isEmpty()) {
            CollectedSignal current = extendedSignals$collectedSignals.pop();

            if (current.boundary() instanceof IRawSignalStateEvaluator signalStateEvaluator) {
                currentSignalState = signalStateEvaluator.computeRawSignalState(
                        current.direction(),
                        upcomingSignalState,
                        train
                        )
                        .setNextState(upcomingSignalState)
                        .setAxisDirection(current.direction())
                        .setDistanceToNextSignal(current.distanceFromPreviousSignal());
            }

            for (ISignalModifier modifier : current.signalModifierSnapshot()) {
                modifier.applyModifier(currentSignalState);
            }

            current.boundary().onSignalScout(
                    current.direction(), currentSignalState, this.train
            );

            if (!current.boundary().skipChaining())
                upcomingSignalState = currentSignalState;
        }
    }
}
