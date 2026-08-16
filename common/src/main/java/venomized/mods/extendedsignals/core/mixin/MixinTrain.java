package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import com.simibubi.create.content.trains.station.GlobalStation;
import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.ITrainDoorData;
import venomized.mods.extendedsignals.core.create.tracks.*;
import venomized.mods.extendedsignals.core.create.tracks.points.ATCController;
import venomized.mods.extendedsignals.core.create.tracks.points.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.MathHelp;
import venomized.mods.extendedsignals.core.util.TrainHelp;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData, ITrain {
    @Unique
    private static final int TICKS_ON_CROSSED_TRIGGERING_DELAY = 20;
    @Unique
    private final List<DelayedSignalCrossTrigger> extendedSignals$frontDelayedOnCrossedTriggering = new ReferenceArrayList<>();
    @Unique
    private final List<DelayedSignalCrossTrigger> extendedSignals$backDelayedOnCrossedTriggering = new ReferenceArrayList<>();
    @Unique
    private final int extendedSignals$shuntRequestCooldown = 0;
    @Shadow
    public ScheduleRuntime runtime;
    @Shadow
    public Navigation navigation;
    @Shadow
    public TrackGraph graph;
    @Shadow
    public List<Carriage> carriages;
    @Shadow
    public UUID id;
    @Shadow
    public double speed;
    @Shadow
    public double targetSpeed;
    @Shadow
    public boolean manualTick;
    @Shadow
    public double throttle;
    @Unique
    private boolean extendedSignals$doorOpen = false;

    @Unique
    private final Map<UUID, TrackEdgePointSignalModifier<?>> trackEdgePointModifiers = new Object2ReferenceArrayMap<>();

    @ModifyReturnValue(method = "frontSignalListener", at = @At("RETURN"), order = 900)
    // Order = 900, Steam n' Rails on NeoForge prematurely cancels our handler so it never gets executed and as such, signals never flip to red.
    public TravellingPoint.IEdgePointListener extendedSignals$hookFrontSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            boolean front = trackEdgePoint.isPrimary(couple.getSecond()
                    .getSecond());// Objects.equals(enteringGroup, signalBoundary.groups.getFirst());

            SignalStateNode signalState = ExtendedSignals.serverNetworkCache().getSignalState(trackEdgePoint.getId(), front);

            if (trackEdgePoint instanceof ATCController atcController) {
                atcController.onATCAction(((Train) (Object) this));
                return false;
            }

            if (signalState != SignalStateNode.INVALID && trackEdgePoint instanceof SignalBoundary) {
                ((INavigation) navigation).extendedSignals$encounteredTrackEdgePointModifiers().values().forEach(e -> {
                    if (e.modifier().onAction(front, ((INavigation) navigation).extendedSignals$currentScoutedEdgePoints(), (Train) (Object) this) == ISignalModifier.ModifierAction.APPLY)
                        e.modifier().applyModifier(signalState);
                });
                throttle = TrainHelp.trainSpeedPercentFromKph(signalState.getMaxProceedSpeed(), (Train) (Object) this, manualTick);
            }

            if (trackEdgePoint instanceof IExtendedEdgePoint<?> signalBoundary) {
                if (speed != 0) {
                    extendedSignals$frontDelayedOnCrossedTriggering.add(
                            new DelayedSignalCrossTrigger(TICKS_ON_CROSSED_TRIGGERING_DELAY, front, signalBoundary)
                    );
                }


                if (trackEdgePoint instanceof TrackEdgePointSignalModifier<?> modifierPoint && navigation != null) {
                    if (modifierPoint.isDiscardMode() && modifierPoint.isAligned(front)) {
                        ((INavigation) navigation).extendedSignals$encounteredTrackEdgePointModifiers().remove(modifierPoint.getType().getId());
                    } else {
                        ((INavigation) navigation).extendedSignals$encounteredTrackEdgePointModifiers().put(modifierPoint.getType().getId(), new EncounteredModifier(front, modifierPoint));
                    }
                    // ISignalModifier.ModifierAction modifierAction = modifierPoint.onAction(front, ((INavigation) navigation).extendedSignals$currentScoutedEdgePoints(), (Train) (Object) this);
                    // if (modifierAction == null) {
                    //     ExtendedSignals.LOGGER.info("A Signal modifier returned *null* as an action. This is undefined behavior: {}", modifierPoint.getClass().getName());
                    //     return false;
                    // }
//
                    // switch (modifierAction) {
                    //     case APPLY -> ((INavigation) navigation).extendedSignals$activeModifiers()
                    //             .put(modifierPoint.getType().getId(), modifierPoint);
                    //     case DISCARD -> ((INavigation) navigation).extendedSignals$activeModifiers()
                    //             .remove(modifierPoint.getType().getId());
                    // }

                    return false;
                }
            }


            return original.test(distance, couple);
        };
    }

    @Inject(method = "arriveAt", at = @At("RETURN"))
    public void extendedSignals$flushReservationsOnArrival(GlobalStation station, CallbackInfo ci) {
        InterlockingManager.clearReservationsForTrain((Train) (Object) this);
        if (navigation != null)
            ((INavigation) navigation).extendedSignals$encounteredTrackEdgePointModifiers().clear();
    }

    @Inject(method = "crash", at = @At("RETURN"))
    public void extendedSignals$flushReservationsOnCrash(CallbackInfo ci) {
        InterlockingManager.clearReservationsForTrain((Train) (Object) this);
        if (navigation != null)
            ((INavigation) navigation).extendedSignals$encounteredTrackEdgePointModifiers().clear();
    }

    @ModifyReturnValue(method = "backSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener extendedSignals$hookBackSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            boolean front = trackEdgePoint.isPrimary(couple.getSecond()
                    .getSecond());// Objects.equals(enteringGroup, signalBoundary.groups.getFirst());

            if (trackEdgePoint instanceof IExtendedEdgePoint<?> signalBoundary) {
                extendedSignals$backDelayedOnCrossedTriggering.add(
                        new DelayedSignalCrossTrigger(TICKS_ON_CROSSED_TRIGGERING_DELAY, front, signalBoundary)
                );
            }

            return original.test(distance, couple);
        };
    }

    @Inject(method = "occupy", at = @At("HEAD"))
    public void extendedSignals$removeReservationOnOccupy(UUID groupId, UUID boundaryId, CallbackInfoReturnable<Boolean> cir) {
        InterlockingManager.clearReservationForTrain((Train) (Object) this, groupId);
    }

    /**
     * @param shuntRequest
     */
    @Override
    public void requestShunting(ShuntRequest shuntRequest) {
        Carriage carriage = carriages.get(shuntRequest.front() ? 0 : carriages.size() - 1);
        TravellingPoint referencePoint = shuntRequest.front() ? carriage.getLeadingPoint() : carriage.getTrailingPoint();
        TravellingPoint shuntScout = new TravellingPoint(
                referencePoint.node1,
                referencePoint.node2,
                referencePoint.edge,
                referencePoint.position,
                referencePoint.upsideDown
        );

        shuntScout.travel(
                this.graph,
                shuntRequest.shuntRequestDistance() * (shuntRequest.front() ? 1 : -1),
                shuntScout.steer(TravellingPoint.SteerDirection.NONE, new Vec3(0, 1, 0)),
                (distance, edgePointCouplePair) -> {
                    TrackEdgePoint point = edgePointCouplePair.getFirst();

                    boolean primary = point.isPrimary(edgePointCouplePair.getSecond().getSecond());

                    if (point instanceof SignalBoundary signalBoundary) {
                        @SuppressWarnings("unchecked")
                        IExtendedEdgePoint<SignalBoundary> boundary = (IExtendedEdgePoint<SignalBoundary>) signalBoundary;
                        boundary.onSignalScout(
                                primary ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                new SignalStateNode().setProceed(true), ((Train) (Object) this), distance
                        );

                        shuntRequest.requester().sendSystemMessage(
                                Component.translatable("message.extendedsignals.train.shunt.ok")
                        );

                        return true;
                    }

                    return false;
                }
        );
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void extendedSignals$onTick(Level level, CallbackInfo ci) {
        extendedSignals$processFrontDelayedCrossCallbacks();
        extendedSignals$processBackDelayedCrossCallbacks();
    }


    @Unique
    private void extendedSignals$processFrontDelayedCrossCallbacks() {
        Iterator<DelayedSignalCrossTrigger> it = extendedSignals$frontDelayedOnCrossedTriggering.iterator();
        while (it.hasNext()) {
            DelayedSignalCrossTrigger delayedSignalCrossTrigger = it.next();
            if (delayedSignalCrossTrigger.getRemainingDelayTicks() <= 0) {
                delayedSignalCrossTrigger.getSignalBoundary()
                        .onSignalCrossedEarly(
                                delayedSignalCrossTrigger.isPrimary() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                (Train) (Object) this
                        );
                it.remove();
                continue;
            }

            delayedSignalCrossTrigger.setRemainingDelayTicks(
                    delayedSignalCrossTrigger.getRemainingDelayTicks() - 1
            );
        }
    }


    @Unique
    private void extendedSignals$processBackDelayedCrossCallbacks() {
        Iterator<DelayedSignalCrossTrigger> it = extendedSignals$backDelayedOnCrossedTriggering.iterator();
        while (it.hasNext()) {
            DelayedSignalCrossTrigger delayedSignalCrossTrigger = it.next();
            if (delayedSignalCrossTrigger.getRemainingDelayTicks() <= 0) {
                delayedSignalCrossTrigger.getSignalBoundary()
                        .onSignalCrossedLate(
                                delayedSignalCrossTrigger.isPrimary() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                (Train) (Object) this
                        );
                it.remove();
                continue;
            }

            delayedSignalCrossTrigger.setRemainingDelayTicks(
                    delayedSignalCrossTrigger.getRemainingDelayTicks() - 1
            );
        }
    }

    /**
     * @return
     */
    @Override
    public boolean extendedSignals$doorForcedClosed() {
        return extendedSignals$doorOpen;
    }

    /**
     * @param closed
     */
    @Override
    public void extendedSignals$setDoorForcedClosed(boolean closed) {
        this.extendedSignals$doorOpen = closed;
    }
}

