package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.create.ITrainDoorData;
import venomized.mods.extendedsignals.core.create.tracks.ATCController;
import venomized.mods.extendedsignals.core.create.tracks.DelayedSignalCrossTrigger;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalBoundaryAccessor;
import venomized.mods.extendedsignals.core.mixin_interfaces.ISignalEdgeGroup;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

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

    @Shadow
    public ScheduleRuntime runtime;
    @Shadow
    public Navigation navigation;
    @Shadow
    public TrackGraph graph;
    @Shadow
    public List<Carriage> carriages;

    @Shadow
    public static Train read(CompoundTag tag, Map<UUID, TrackGraph> trackNetworks, DimensionPalette dimensions) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    public UUID id;
    @Unique
    private boolean extendedSignals$doorOpen = false;
    @Unique
    private final int extendedSignals$shuntRequestCooldown = 0;

    @ModifyReturnValue(method = "frontSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener extendedSignals$hookFrontSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            boolean front = trackEdgePoint.isPrimary(couple.getSecond()
                    .getSecond());// Objects.equals(enteringGroup, signalBoundary.groups.getFirst());


            if (trackEdgePoint instanceof ATCController atcController) {
                atcController.onATCAction(((Train) (Object) this));
                return false;
            }

            if (trackEdgePoint instanceof IExtendedSignalBoundary<?> signalBoundary) {
                extendedSignals$frontDelayedOnCrossedTriggering.add(
                        new DelayedSignalCrossTrigger(TICKS_ON_CROSSED_TRIGGERING_DELAY, front, signalBoundary)
                );


                if (trackEdgePoint instanceof TrackEdgePointSignalModifier<?> modifier && navigation != null) {
                    if (modifier.isAligned(modifier.isPrimary(couple.getSecond().getSecond()))) {
                        if (modifier.shouldApply()) {
                            ((INavigation) navigation).extendedSignals$activeModifiers()
                                    .put(modifier.getType().getId(), modifier);
                        } else {
                            ((INavigation) navigation).extendedSignals$activeModifiers()
                                    .remove(modifier.getType().getId());
                        }
                    }
                }
            }

            return original.test(distance, couple);
        };
    }

    @ModifyReturnValue(method = "backSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener extendedSignals$hookBackSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            boolean front = trackEdgePoint.isPrimary(couple.getSecond()
                    .getSecond());// Objects.equals(enteringGroup, signalBoundary.groups.getFirst());

            if (trackEdgePoint instanceof IExtendedSignalBoundary<?> signalBoundary) {
                extendedSignals$backDelayedOnCrossedTriggering.add(
                        new DelayedSignalCrossTrigger(TICKS_ON_CROSSED_TRIGGERING_DELAY, front, signalBoundary)
                );
            }

            return original.test(distance, couple);
        };
    }

    @Inject(method = "lambda$backSignalListener$10", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/signal/SignalBoundary;getGroup(Lcom/simibubi/create/content/trains/graph/TrackNode;)Ljava/util/UUID;"))
    public void extendedSignals$releaseOwnedSignalGroups(Double distance, Pair<TrackEdgePoint, Couple<TrackNode>> couple, CallbackInfoReturnable<Boolean> cir, @Local(name = "signal") SignalBoundary signalBoundary) {
        boolean primary = couple.getFirst().isPrimary(couple.getSecond().getSecond());

        UUID front = signalBoundary.groups.get(primary);
        UUID back = signalBoundary.groups.get(!primary);

        INavigation nav = (INavigation) navigation;
        if (!nav.extendedSignals$ownedReservedSignals().contains(back))
            return;

        SignalEdgeGroup group = Create.RAILWAYS.signalEdgeGroups.get(back);
        if (group == null)
            return;

        if (((ISignalEdgeGroup) group).extendedSignals$isReservedByOtherTrain((Train) (Object) this)) {
            ExtendedSignalsCore.LOGGER.warn(
                    "Train {} was willing to clear a reservation made by another train for group {}, " +
                            "this should not happen!", id, group.id
            );
        }

        ((ISignalEdgeGroup) group).extendedSignals$setReservedByTrain(null);
        nav.extendedSignals$ownedReservedSignals().remove(back);
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
                        IExtendedSignalBoundary<SignalBoundary> boundary = (IExtendedSignalBoundary<SignalBoundary>) signalBoundary;
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

