package venomized.mods.extendedsignals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mods.extendedsignals.core.RawSignalState;
import venomized.mods.extendedsignals.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.create.tracks.IRawSignalStateEvaluator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation {
    @Unique
    private static final double LOOK_AHEAD_DISTANCE = 128;
    @Unique
    private final Deque<Pair<SignalBoundary, Direction.AxisDirection>> extendedSignals$collectedSignals = new LinkedList<>();
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

        final double lookaheadDistance = Math.min(
                LOOK_AHEAD_DISTANCE,
                Math.min(distanceToDestination, distanceToSignal)
        );

        this.extendedSignals$collectedSignals.clear();
        extendedSignals$signalScoutTriggerCollector.travel(
                train.graph,
                lookaheadDistance * speedMod,
                controlSignalScout(),
                this::extendedSignals$collectSignalsInPath
        );
        extendedSignals$distantSignallingLogic();
    }

    @Unique
    private boolean extendedSignals$collectSignalsInPath(double distance, Pair<TrackEdgePoint, Couple<TrackNode>> trackEdgePointCouplePair) {
        TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();
        if (!(trackEdgePoint instanceof SignalBoundary signalBoundary))
            return false;


        UUID enteringGroup = signalBoundary.getGroup(
                trackEdgePointCouplePair.getSecond().getSecond()
        );
        boolean side = Objects.equals(enteringGroup, signalBoundary.groups.getFirst());
        extendedSignals$collectedSignals.push(
                Pair.of(signalBoundary, side ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE)
        );

        return signalBoundary.cachedStates.get(side) == SignalBlockEntity.SignalState.RED;
    }

    @Unique
    private void extendedSignals$distantSignallingLogic() {
        RawSignalState upcoming = null;

        while (!extendedSignals$collectedSignals.isEmpty()) {
            Pair<SignalBoundary, Direction.AxisDirection> pair = extendedSignals$collectedSignals.pop();
            SignalBoundary current = pair.getFirst();
            Direction.AxisDirection currentDirection = pair.getSecond();
            boolean primary = currentDirection == Direction.AxisDirection.POSITIVE;

            RawSignalState newState = ((IRawSignalStateEvaluator) current).computeRawSignalState(
                    currentDirection, upcoming, train.reservedSignalBlocks.contains(current.groups.get(primary))
            );
            ((IExtendedSignalBoundary) current).extendedSignal$onScout(
                    currentDirection, newState, this.train
            );

            upcoming = newState;
        }
    }
}
