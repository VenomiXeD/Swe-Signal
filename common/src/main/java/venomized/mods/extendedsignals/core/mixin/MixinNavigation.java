package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.create.tracks.CollectedSignal;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.IRawSignalStateEvaluator;

import java.util.*;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation {
    @Unique
    private static final double LOOK_AHEAD_DISTANCE = 128;
    @Unique
    private final Deque<CollectedSignal> extendedSignals$collectedSignals = new ArrayDeque<>();
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
        extendedSignals$distantSignallingLogic();
    }

    @Unique
    private void extendedSignals$collectSignalsInPath(double speedMod) {
        final double lookAheadDistance = Math.min(
                LOOK_AHEAD_DISTANCE,
                Math.min(distanceToDestination, distanceToSignal)
        );

        this.extendedSignals$collectedSignals.clear();
        final MutableDouble previousSignalDistance = new MutableDouble(-1);
        extendedSignals$signalScoutTriggerCollector.travel(
                train.graph,
                lookAheadDistance * speedMod,
                controlSignalScout(),
                (distance, trackEdgePointCouplePair) -> {
                    TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();

                    if (!(trackEdgePoint instanceof IExtendedSignalBoundary signalBoundary))
                        return false;

                    // UUID enteringGroup = signalBoundary.getGroup(
                    //         trackEdgePointCouplePair.getSecond().getSecond()
                    // );
                    boolean side = trackEdgePoint.isPrimary(trackEdgePointCouplePair.getSecond()
                            .getSecond()
                    );
                    //Objects.equals(enteringGroup, signalBoundary.groups.getFirst());

                    double deltaSignalDistance = previousSignalDistance.getValue() < 0
                            ? -1.0 : distance - previousSignalDistance.getValue();
                    previousSignalDistance.setValue(distance);

                    extendedSignals$collectedSignals.push(
                            new CollectedSignal(
                                    signalBoundary,
                                    side ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                    distance,
                                    deltaSignalDistance
                            )
                    );

                    if (signalBoundary instanceof SignalBoundary createTrueSignalBoundary)
                        return createTrueSignalBoundary.cachedStates.get(side) == SignalBlockEntity.SignalState.RED;

                    return false;
                }
        );
    }

    @Unique
    private void extendedSignals$distantSignallingLogic() {
        RawSignalState upcomingSignalState = null;
        RawSignalState currentSignalState = RawSignalState.INVALID;

        while (!extendedSignals$collectedSignals.isEmpty()) {
            CollectedSignal current = extendedSignals$collectedSignals.pop();
            boolean primary = current.direction() == Direction.AxisDirection.POSITIVE;

            if (current.boundary() instanceof IRawSignalStateEvaluator signalStateEvaluator) {
                currentSignalState = signalStateEvaluator.computeRawSignalState(
                        current.direction(),
                        upcomingSignalState,
                        train
                ).setNextState(upcomingSignalState).setAxisDirection(current.direction());
            }

            current.boundary().onSignalScout(
                    current.direction(), currentSignalState, this.train
            );

            if (!current.boundary().skipChaining())
                upcomingSignalState = currentSignalState;
        }
    }
}
