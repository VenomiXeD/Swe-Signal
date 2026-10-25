package venomized.mods.extendedsignals.core.create;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import lombok.Getter;
import lombok.Setter;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableDouble;
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
import venomized.mods.extendedsignals.core.mixin.AccessorNavigation;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.lang.ref.WeakReference;
import java.util.*;

public class AdvancedSignalScout {
    private final Train train;
    private final Navigation navigation;

    private static final int SIGNAL_SCOUT_INTERVAL = 10;
    @Getter
    private final ReferenceArrayList<CollectedEdgePoint> persistentCollectedEdgePoints = new ReferenceArrayList<>();
    @Getter
    private final ReferenceArrayList<CollectedEdgePoint> collectedEdgePoints = new ReferenceArrayList<>();
    @Getter
    private final Map<ResourceLocation, EncounteredPoint> activeModifiers = new Object2ReferenceLinkedOpenHashMap<>();
    private final TravellingPoint signalScoutTriggerCollector = new TravellingPoint();
    @Getter
    @Setter
    private long signalScoutCooldown = 0;

    @Getter
    @Setter
    private SignalPolicy signalPolicyMode;

    @Getter
    private Deque<SwitchSelection> switchSelections = new ArrayDeque<>();

    public AdvancedSignalScout(Train train, Navigation navigation) {
        this.train = train;
        this.navigation = navigation;

        signalPolicyMode = SignalPolicy.JOURNEY;
    }

    public static AdvancedSignalScout from(Train train) {
        return ITrain.cast(train).getAdvancedSignalScout();
    }

    public void tick(Level level) {
        if (signalScoutCooldown-- > 0 && train.speed != 0)
            return;
        signalScoutCooldown = SIGNAL_SCOUT_INTERVAL;

        boolean backwards = train.manualTick ? train.speed < 0 : navigation.destinationBehindTrain;
        double speedMod = backwards ? -1 : 1; // navigation.destinationBehindTrain ? -1 : 1;
        TravellingPoint leadingPoint = /*!navigation.destinationBehindTrain*/ !backwards ? train.carriages.getFirst()
                .getLeadingPoint()
                : train.carriages.getLast()
                .getTrailingPoint();

        collectSignals((ServerLevel) level, leadingPoint, speedMod);
        computeSignalStates();
    }

    private void collectSignals(ServerLevel level, TravellingPoint leadingPoint, double speedMod) {
        final double lookAheadDistance = train.speed == 0 ? 0 : Math.min(
                ExtendedSignalsConfig.SERVER.defaultScanDistance.get(),
                train.manualTick ? Double.MAX_VALUE : Math.min(navigation.distanceToDestination, navigation.distanceToSignal)
        );

        signalScoutTriggerCollector.node1 = leadingPoint.node1;
        signalScoutTriggerCollector.node2 = leadingPoint.node2;
        signalScoutTriggerCollector.edge = leadingPoint.edge;
        signalScoutTriggerCollector.position = leadingPoint.position;

        TravellingPoint signalScout = ((AccessorNavigation) navigation).getSignalScout();
        if (train.manualTick || signalScout.edge == null) {
            signalScout.node1 = leadingPoint.node1;
            signalScout.node2 = leadingPoint.node2;
            signalScout.edge = leadingPoint.edge;
            signalScout.position = leadingPoint.position;
        }
        TravellingPoint.ITrackSelector signalScoutTrackSelector = navigation.controlSignalScout();

        collectedEdgePoints.clear();

        // Deque<SwitchSelection> scoutSwitchQueue = new ArrayDeque<>(switchSelections);
        List<Pair<Boolean, List<Map.Entry<TrackNode, TrackEdge>>>> switches = new ReferenceArrayList<>();
        final MutableDouble previousSignalDistance = new MutableDouble(-1);
        signalScoutTriggerCollector.travel(
                train.graph,
                (lookAheadDistance + 5f) * speedMod,
                (a, b) -> {
                    // Vec3i v = b.getSecond().getFirst().getValue().node1.getLocation();
                    // level.sendParticles(ParticleTypes.FIREWORK, v.getX()/2f, v.getY()/2f, v.getZ()/2f, 5600, 0, 0, 0, 0.5f);
                    // level.setBlock(new BlockPos(v.above(4)), level.getGameTime() % 2 == 0 ? Blocks.GLASS.defaultBlockState() : Blocks.STONE.defaultBlockState(), 2);
                    switches.add(b);
                    return signalScoutTrackSelector.apply(a, b);
                },
                (distance, trackEdgePointCouplePair) -> {
                    // if (!switches.isEmpty() && train.manualTick) {
                    //     return true;
                    // }

                    TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();
                    boolean front = trackEdgePoint.isPrimary(trackEdgePointCouplePair.getSecond()
                            .getSecond()
                    );

                    if (!(trackEdgePoint instanceof IExtendedEdgePoint extendedSignalPoint)) {
                        return false;
                    }
                    double deltaSignalDistance = previousSignalDistance.getValue() < 0
                            ? -1.0 : distance - previousSignalDistance.getValue();

                    if (trackEdgePoint instanceof ISignal signal && signal.isMainSignal(front))
                        previousSignalDistance.setValue(distance);


                    boolean waiting = isWaitingAtPoint(trackEdgePoint, front);
                    collectedEdgePoints.push(
                            new CollectedEdgePoint(
                                    new WeakReference<>(extendedSignalPoint),
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
        // if (!switches.isEmpty()) {
        //     System.out.println("switches count: " + switches.size());
        // }
        persistentCollectedEdgePoints.clear();
        persistentCollectedEdgePoints.addAll(collectedEdgePoints);

        final Map<ResourceLocation, WeakReference<ISignalStateModifier>> predictedModifiers = new Object2ReferenceArrayMap<>();
        for (Map.Entry<ResourceLocation, EncounteredPoint> entry : activeModifiers.entrySet()) {
            ResourceLocation key = entry.getKey();
            EncounteredPoint value = entry.getValue();
            ISignalStateModifier modifierPoint = value.modifier().get();
            if (modifierPoint == null)
                continue;

            ISignalStateModifier.ModifierAction action =
                    modifierPoint.onAction(value.front(), persistentCollectedEdgePoints, train);

            if (action == ISignalStateModifier.ModifierAction.APPLY)
                predictedModifiers.put(key, value.modifier());
            else if (action == ISignalStateModifier.ModifierAction.DISCARD)
                predictedModifiers.remove(key);
        }

        for (CollectedEdgePoint collected : collectedEdgePoints) {
            IExtendedEdgePoint boundary = collected.boundary().get();
            if (boundary == null)
                continue;

            if (boundary instanceof TrackEdgePointSignalModifier<?> modifierPoint) {
                ISignalStateModifier.ModifierAction modifierAction = modifierPoint.onAction(
                        collected.signalDirection() == Direction.AxisDirection.POSITIVE,
                        persistentCollectedEdgePoints, train);
                if (modifierAction == null) {
                    ExtendedSignals.LOGGER.info("A Signal modifier returned *null* as an action. This is undefined behavior: {}", modifierPoint.getClass().getName());
                    continue;
                }

                switch (modifierAction) {
                    case APPLY -> predictedModifiers
                            .put(modifierPoint.getType().getId(), new WeakReference<>(modifierPoint));
                    case DISCARD -> predictedModifiers
                            .remove(modifierPoint.getType().getId());
                }
            }
            collected.signalModifierSnapshot().addAll(predictedModifiers.values());
        }
    }

    private boolean isWaitingAtPoint(TrackEdgePoint trackEdgePoint, boolean primary) {
        if (((navigation.waitingForSignal != null) && Objects.equals(trackEdgePoint.id, navigation.waitingForSignal.getFirst())) && primary == navigation.waitingForSignal.getSecond())
            return true;

        boolean blockOccupied = false;
        if (trackEdgePoint instanceof SignalBoundary createSignalBoundary) {
            UUID entering = createSignalBoundary.groups.get(primary);
            SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get(entering);
            if (signalEdgeGroup != null)
                blockOccupied = (!signalEdgeGroup.trains.contains(train) && !signalEdgeGroup.trains.isEmpty()) ||
                        createSignalBoundary.isForcedRed(primary) ||
                        InterlockingManager.trainOwnsGroupIntersecting(train, signalEdgeGroup) == InterlockingManager.ReservationResult.CONFLICT;
        }
        return blockOccupied;
    }

    private void computeSignalStates() {
        SignalStateNode upcomingSignalState = null;
        SignalStateNode currentSignalState = null;
        CollectedEdgePoint upcoming = null;
        CollectedEdgePoint current = null;

        while (!collectedEdgePoints.isEmpty()) {
            current = collectedEdgePoints.pop();
            IExtendedEdgePoint iExtendedEdgePoint = current.boundary().get();
            if (iExtendedEdgePoint == null) {
                continue;
            }

            TrackEdgePoint edgePoint = (TrackEdgePoint) iExtendedEdgePoint;
            if (iExtendedEdgePoint instanceof ISignal) {
                if (iExtendedEdgePoint instanceof ISignalStateEvaluator evaluator)
                    currentSignalState = evaluator.evaluateSignalState(current.signalDirection(), upcomingSignalState, train);
                else
                    currentSignalState = ExtendedSignals.serverNetworkCache().getSignalState(edgePoint.getId(), current.signalDirection() == Direction.AxisDirection.POSITIVE);

                currentSignalState.setDistanceToNextSignal(upcomingSignalState == null ? -1 : upcoming.distance() - current.distance())
                        .setNextState(upcomingSignalState)
                        .setProceed(!current.isStoppingAtThisNode())
                        .setReservedBy(train.id)
                        .setMaxProceedSpeed(Double.MAX_VALUE); // Reset the speed value to max, modifiers will end up clamping it
            }

            if (currentSignalState != null) {
                for (ISignalStateModifier modifier : current.signalModifierSnapshot().stream().map(WeakReference::get).filter(Objects::nonNull).toList()) {
                    modifier.applyModifier(currentSignalState);
                }
            }

            if (edgePoint instanceof ISignal signalPoint && signalPoint.isMainSignal(current.signalDirection() == Direction.AxisDirection.POSITIVE)) {
                upcomingSignalState = currentSignalState;
                upcoming = current;
            }

            iExtendedEdgePoint.onSignalScout(
                    current.signalDirection(), currentSignalState, train, current.distance()
            );
        }

        if (current != null && train.manualTick)
            navigation.distanceToSignal = current.distance();
    }

    public void onPassSwitch(boolean direction, TrackNode targetNode) {
    }
}
