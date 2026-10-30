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
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundMiscTrainDataPacket;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.lang.ref.WeakReference;
import java.util.*;

public class AdvancedSignalScout {
    private static final int SIGNAL_SCOUT_INTERVAL = 10;

    private final Train train;
    private final Navigation navigation;

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
    private SignalPolicy signalPolicyMode;

    public void setSignalPolicyMode(SignalPolicy signalPolicyMode) {
        this.signalPolicyMode = signalPolicyMode;
        ClientBoundMiscTrainDataPacket.broadcastTrainUpdate(train, false);
    }

    @Getter
    private Deque<SwitchSelection> switchSelections = new ArrayDeque<>();

    private boolean manualTrainDriving;
    public AdvancedSignalScout(Train train, Navigation navigation) {
        this.train = train;
        this.navigation = navigation;
    }

    public static AdvancedSignalScout from(Train train) {
        return ITrain.cast(train).advancedSignalScout();
    }

    public void tick(Level level) {
        manualTrainDriving = ITrain.cast(train).isManual();

        if (signalScoutCooldown-- > 0 && train.speed != 0)
            return;

        if (signalPolicyMode == null) {
            ExtendedSignals.LOGGER.info("Train {} was missing a signal policy mode. Defaulting to Journey", train.name.getString());
            signalPolicyMode = SignalPolicy.JOURNEY;
        }

        signalScoutCooldown = SIGNAL_SCOUT_INTERVAL;
        collectSignals(level);
        computeSignalStates();
    }

    private void collectSignals(Level level) {
        INavigation nav = INavigation.from(train);
        double lookAheadDistance;
        double speedMod = 0;


        if (manualTrainDriving) {
            // When manually driving, we need to get our own leadingPoint object
            speedMod = -Mth.sign(train.speed);

            TravellingPoint leadingPoint = train.speed > 0
                    ? train.carriages.getFirst().getLeadingPoint()
                    : train.carriages.getLast().getTrailingPoint();

            // Manually assign the signal scout values so we can replicate
            TravellingPoint signalScout = ((AccessorNavigation) navigation).signalScout();
            signalScout.node1 = leadingPoint.node1;
            signalScout.node2 = leadingPoint.node2;
            signalScout.edge = leadingPoint.edge;
            signalScout.position = leadingPoint.position;

            signalScoutTriggerCollector.node1 = leadingPoint.node1;
            signalScoutTriggerCollector.node2 = leadingPoint.node2;
            signalScoutTriggerCollector.edge = leadingPoint.edge;
            signalScoutTriggerCollector.position = leadingPoint.position;

            lookAheadDistance = train.speed == 0 ? 0 : ExtendedSignalsConfig.SERVER.defaultScanDistance.get();
        } else {
            speedMod = INavigation.from(train).extendedSignals$speedMod();

            // TravellingPoint leadingPoint = !navigation.destinationBehindTrain
            //         ? train.carriages.getFirst().getLeadingPoint()
            //         : train.carriages.getLast().getTrailingPoint();

            // For automatic routing, we can simply reuse the signal scout values that are captured by the mixin
            signalScoutTriggerCollector.node1 = nav.extendedSignals$startScoutNode1();
            signalScoutTriggerCollector.node2 = nav.extendedSignals$startScoutNode2();
            signalScoutTriggerCollector.edge = nav.extendedSignals$startScoutEdge();

            // Shift it a bit back - otherwise it can't see the track edge point it's currently standing on
            signalScoutTriggerCollector.position = nav.extendedSignals$startScoutPosition();// - (speedMod * 0.1f);

            NavigationDistanceValuesSnapshot values = nav.extendedSignals$postSignalScoutCapturedValues();
            lookAheadDistance = Math.min(
                    ExtendedSignalsConfig.SERVER.defaultScanDistance.get(),
                    Math.min(values.distanceToDestination(), values.distanceToSignal())
            );

            if (train.name.getString().equals("DBG"))
                ExtendedSignals.LOGGER.info("look dist: {} {} {}(clamp, dest, sign), speedmod: {}", lookAheadDistance, values.distanceToDestination(), values.distanceToSignal(), speedMod);
        }

        collectedEdgePoints.clear();

        // Deque<SwitchSelection> scoutSwitchQueue = new ArrayDeque<>(switchSelections);
        List<Pair<Boolean, List<Map.Entry<TrackNode, TrackEdge>>>> switches = new ReferenceArrayList<>();
        final MutableDouble previousSignalDistance = new MutableDouble(-1);

        // if (lookAheadDistance < 5 && train.speed != 0)
        // {
        //     ExtendedSignals.LOGGER.info("Train {} has a very short lookAhead distance", train.name.getString());
        // }

        if (((AccessorNavigation) navigation).signalScout().edge == null) {
            ExtendedSignals.LOGGER.warn("Signal Scout Edge was null...? Should probably not happen; prevented crashing from NPE");
            return;
        }

        TravellingPoint.ITrackSelector signalScoutTrackSelector = navigation.controlSignalScout();
        signalScoutTriggerCollector.travel(
                train.graph,
                (lookAheadDistance + 1f) * speedMod,
                signalScoutTrackSelector,// (a, b) -> {
                //     // Vec3i v = b.getSecond().getFirst().getValue().node1.getLocation();
                //     // level.sendParticles(ParticleTypes.FIREWORK, v.getX()/2f, v.getY()/2f, v.getZ()/2f, 5600, 0, 0, 0, 0.5f);
                //     // level.setBlock(new BlockPos(v.above(4)), level.getGameTime() % 2 == 0 ? Blocks.GLASS.defaultBlockState() : Blocks.STONE.defaultBlockState(), 2);
                //     switches.add(b);
                //     return signalScoutTrackSelector.apply(a, b);
                // },
                (distance, point) -> {
                    // if (!switches.isEmpty() && train.manualTick) {
                    //     return true;
                    // }

                    TrackEdgePoint trackEdgePoint = point.getFirst();
                    boolean front = trackEdgePoint.isPrimary(point.getSecond().getSecond());

                    if (!(trackEdgePoint instanceof IExtendedEdgePoint extendedSignalPoint)) {
                        return false;
                    }
                    double deltaSignalDistance = previousSignalDistance.getValue() < 0
                            ? -1.0 : distance - previousSignalDistance.getValue();

                    if (trackEdgePoint instanceof ISignal signal && signal.isMainSignal(front))
                        previousSignalDistance.setValue(distance);

                    boolean occupied = isWaitingAtPoint(trackEdgePoint, front);
                    collectedEdgePoints.push(
                            new CollectedEdgePoint(
                                    new WeakReference<>(extendedSignalPoint),
                                    front ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                    occupied,
                                    distance,
                                    deltaSignalDistance,
                                    new ReferenceArrayList<>()
                            )
                    );
                    return occupied && !bypassesRedSignals();
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
        NavigationDistanceValuesSnapshot values = INavigation.from(train).extendedSignals$postSignalScoutCapturedValues();
        if ((values.waitingForSignal() != null && values.waitingForSignal().getFirst().equals(trackEdgePoint.id)) && primary == values.waitingForSignal().getSecond())
            return true;

        boolean blockOccupied = false;
        if (trackEdgePoint instanceof SignalBoundary createSignalBoundary) {
            UUID entering = createSignalBoundary.groups.get(primary);
            SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get(entering);
            if (signalEdgeGroup != null)
                blockOccupied =
                        (!signalEdgeGroup.trains.contains(train) && !signalEdgeGroup.trains.isEmpty()) ||
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

                boolean shuntingPermitted =
                        signalPolicyMode == SignalPolicy.SHUNT_COUPLING || signalPolicyMode == SignalPolicy.SHUNT_MOVEMENT && !current.obstacle();

                boolean journeyPermitted =
                        !current.obstacle() && signalPolicyMode == SignalPolicy.JOURNEY && !shuntingPermitted;

                currentSignalState.setDistanceToNextSignal(upcomingSignalState == null ? -1 : upcoming.distance() - current.distance())
                        .setNextState(upcomingSignalState)
                        .setJourneyPermitted(journeyPermitted)
                        .setShuntingPermitted(shuntingPermitted)
                        .setObstacle(current.obstacle())
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

        // if (current != null && current.obstacle() && manualTrainDriving)
        //     navigation.distanceToSignal = current.distance();
    }

    public boolean bypassesRedSignals() {
        return signalPolicyMode == SignalPolicy.SHUNT_COUPLING;
    }

    public void onPassSwitch(boolean direction, TrackNode targetNode) {
    }

    public void fromNBT(CompoundTag tag) {
        if (tag.contains("signal_mode"))
            signalPolicyMode = NBTHelper.readEnum(tag, "signal_mode", SignalPolicy.class);
    }

    public void toNBT(CompoundTag tag) {
        if (signalPolicyMode != null)
            NBTHelper.writeEnum(tag, "signal_mode", signalPolicyMode);
    }

}
