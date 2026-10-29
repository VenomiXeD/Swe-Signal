package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.*;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import com.simibubi.create.content.trains.station.GlobalStation;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;
import venomized.mods.extendedsignals.core.create.ITrainDoorData;
import venomized.mods.extendedsignals.core.create.SignalPolicy;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.DelayedSignalCrossTrigger;
import venomized.mods.extendedsignals.core.create.tracks.EncounteredPoint;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;
import venomized.mods.extendedsignals.core.create.tracks.points.*;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundMiscTrainDataPacket;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.util.TrackedValue;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData, ITrain {
    @Unique
    private List<DelayedSignalCrossTrigger> extendedSignals$frontDelayedOnCrossedTriggering;
    @Unique
    private List<DelayedSignalCrossTrigger> extendedSignals$backDelayedOnCrossedTriggering;
    @Unique
    private TrackedValue<Double> extendedSignals$speedTracker;

    /**
     * @return
     */
    @Override
    public TrackedValue<Double> extendedSignals$trackedSpeed() {
        return extendedSignals$speedTracker;
    }

    @Unique
    private AdvancedSignalScout extendedSignals$advancedSignalScout;
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
    public boolean manualTick;
    @Shadow
    public double throttle;
    @Unique
    private boolean extendedSignals$doorOpen = false;

    @Unique
    private void extendedSignals$onSpeedChanged(double oldValue, double newValue) {
        if (oldValue == 0) {
            extendedSignals$advancedSignalScout.setSignalScoutCooldown(0);
        }
        ClientBoundMiscTrainDataPacket.broadcastTrainUpdate(((Train) (Object) this), false);
    }

    @Inject(method = "<init>(Ljava/util/UUID;Ljava/util/UUID;Lcom/simibubi/create/content/trains/graph/TrackGraph;Ljava/util/List;Ljava/util/List;ZLnet/minecraft/network/chat/Component;Lcom/simibubi/create/content/trains/entity/TrainIconType;I)V", at = @At("RETURN"))
    public void extendedSignals$Ctor(UUID id, UUID owner, TrackGraph graph, List carriages, List carriageSpacing, boolean doubleEnded, Component name, TrainIconType icon, int mapColorIndex, CallbackInfo ci) {
        extendedSignals$speedTracker = new TrackedValue<>(0d, this::extendedSignals$onSpeedChanged);

        extendedSignals$backDelayedOnCrossedTriggering = new ReferenceArrayList<>();
        extendedSignals$frontDelayedOnCrossedTriggering = new ReferenceArrayList<>();

        extendedSignals$advancedSignalScout = new AdvancedSignalScout((Train) (Object) this, navigation);
    }


    @ModifyExpressionValue(method = "lambda$frontSignalListener$14", at = @At(value = "INVOKE", target = "Ljava/util/UUID;equals(Ljava/lang/Object;)Z"))
    public boolean extendedSignals$ignoreRedSignalsAtFrontListener(boolean original) {
        return original && !extendedSignals$advancedSignalScout.bypassesRedSignals();
    }

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


            if (signalState != null && trackEdgePoint instanceof ISignal) {
                extendedSignals$encounteredPoints().values().forEach(e -> {
                    ISignalStateModifier mod = e.modifier().get();
                    if (mod == null)
                        return;

                    if (mod.onAction(front, extendedSignals$currentScoutedEdgePoints(), (Train) (Object) this) == ISignalStateModifier.ModifierAction.APPLY)
                        mod.applyModifier(signalState);
                });
            }

            if (trackEdgePoint instanceof ITrainSpeedModifier speedPoint) {
                speedPoint.applySpeed(front, extendedSignals$currentScoutedEdgePoints(), ((Train) (Object) this));
            }

            if (trackEdgePoint instanceof IExtendedEdgePoint signalBoundary) {
                signalBoundary.onSignalCrossedImmediate(front ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE, signalState, ((Train) (Object) this));
                if (navigation.distanceToSignal != 0 || ITrain.cast((Train) (Object) this).isManual()) {
                    extendedSignals$frontDelayedOnCrossedTriggering.add(
                            new DelayedSignalCrossTrigger(ExtendedSignalsConfig.SERVER.signalPassedStateChangeDelay.getAsInt(), front, signalBoundary)
                    );
                }


                if (trackEdgePoint instanceof TrackEdgePointSignalModifier<?> modifierPoint) {
                    if (modifierPoint.isAligned(front)) {
                        if (modifierPoint.isDiscardMode()) {
                            extendedSignals$encounteredPoints().remove(modifierPoint.getType().getId());
                        } else {
                            extendedSignals$encounteredPoints().put(modifierPoint.getType().getId(), new EncounteredPoint(front, new WeakReference<>(modifierPoint)));
                        }
                    }
                }
            }


            return original.test(distance, couple);
        };
    }

    @Unique
    private Map<ResourceLocation, EncounteredPoint> extendedSignals$encounteredPoints() {
        return extendedSignals$advancedSignalScout.getActiveModifiers();
    }

    @Unique
    private List<CollectedEdgePoint> extendedSignals$currentScoutedEdgePoints() {
        return extendedSignals$advancedSignalScout.getCollectedEdgePoints();
    }

    @Inject(method = "arriveAt", at = @At("RETURN"))
    public void extendedSignals$flushReservationsOnArrival(GlobalStation station, CallbackInfo ci) {
        ClientBoundMiscTrainDataPacket.broadcastTrainUpdate((Train) (Object) this, true);

        InterlockingManager.clearReservationsForTrain((Train) (Object) this);
        if (navigation != null)
            extendedSignals$encounteredPoints().clear();
    }

    @Inject(method = "crash", at = @At("RETURN"))
    public void extendedSignals$flushReservationsOnCrash(CallbackInfo ci) {
        ClientBoundMiscTrainDataPacket.broadcastTrainUpdate((Train) (Object) this, true);

        InterlockingManager.clearReservationsForTrain((Train) (Object) this);
        if (navigation != null)
            extendedSignals$encounteredPoints().clear();
    }

    @ModifyReturnValue(method = "backSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener extendedSignals$hookBackSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            boolean front = trackEdgePoint.isPrimary(couple.getSecond()
                    .getSecond());// Objects.equals(enteringGroup, signalBoundary.groups.getFirst());

            if (trackEdgePoint instanceof IExtendedEdgePoint signalBoundary) {
                extendedSignals$backDelayedOnCrossedTriggering.add(
                        new DelayedSignalCrossTrigger(ExtendedSignalsConfig.SERVER.signalPassedStateChangeDelay.getAsInt(), front, signalBoundary)
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

        // shuntScout.travel(
        //         this.graph,
        //         shuntRequest.shuntRequestDistance() * (shuntRequest.front() ? 1 : -1),
        //         shuntScout.steer(TravellingPoint.SteerDirection.NONE, new Vec3(0, 1, 0)),
        //         (distance, edgePointCouplePair) -> {
        //             TrackEdgePoint point = edgePointCouplePair.getFirst();
//
        //             boolean primary = point.isPrimary(edgePointCouplePair.getSecond().getSecond());
//
        //             if (point instanceof SignalBoundary signalBoundary) {
        //                 @SuppressWarnings("unchecked")
        //                 IExtendedEdgePoint boundary = (IExtendedEdgePoint) signalBoundary;
        //                 boundary.onSignalScout(
        //                         primary ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
        //                         new SignalStateNode().setProceed(true), ((Train) (Object) this), distance
        //                 );
//
        //                 shuntRequest.requester().sendSystemMessage(
        //                         Component.translatable("message.extendedsignals.train.shunt.ok")
        //                 );
//
        //                 return true;
        //             }
//
        //             return false;
        //         }
        // );
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void extendedSignals$returnTick(Level level, CallbackInfo ci) {
        extendedSignals$processFrontDelayedCrossCallbacks();
        extendedSignals$processBackDelayedCrossCallbacks();
        extendedSignals$speedTracker.change(speed);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/entity/Navigation;tick(Lnet/minecraft/world/level/Level;)V", shift = At.Shift.AFTER))
    public void extendedSignals$advancedSignalScoutTick(Level level, CallbackInfo ci) {
        extendedSignals$advancedSignalScout.tick(level);
    }


    @Unique
    private void extendedSignals$processFrontDelayedCrossCallbacks() {
        Iterator<DelayedSignalCrossTrigger> it = extendedSignals$frontDelayedOnCrossedTriggering.iterator();
        while (it.hasNext()) {
            DelayedSignalCrossTrigger delayedSignalCrossTrigger = it.next();
            if (delayedSignalCrossTrigger.getRemainingDelayTicks() <= 0) {
                SignalStateNode signalState = delayedSignalCrossTrigger.getSignalBoundary() instanceof ISignal
                        ? ExtendedSignals.serverNetworkCache().getSignalState(((TrackEdgePoint) delayedSignalCrossTrigger.getSignalBoundary()).getId(), delayedSignalCrossTrigger.isPrimary())
                        : null;

                delayedSignalCrossTrigger.getSignalBoundary()
                        .onSignalCrossedFrontDelayed(
                                delayedSignalCrossTrigger.isPrimary() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE, signalState,
                                (Train) (Object) this);
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
                SignalStateNode signalState = delayedSignalCrossTrigger.getSignalBoundary() instanceof ISignal
                        ? ExtendedSignals.serverNetworkCache().getSignalState(((TrackEdgePoint) delayedSignalCrossTrigger.getSignalBoundary()).getId(), delayedSignalCrossTrigger.isPrimary())
                        : null;

                delayedSignalCrossTrigger.getSignalBoundary()
                        .onSignalCrossedLate(
                                delayedSignalCrossTrigger.isPrimary() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE, signalState,
                                (Train) (Object) this);
                it.remove();
                continue;
            }

            delayedSignalCrossTrigger.setRemainingDelayTicks(
                    delayedSignalCrossTrigger.getRemainingDelayTicks() - 1
            );
        }
    }

    @ModifyReturnValue(method = "write", at = @At("RETURN"))
    public CompoundTag extendedSignals$write(CompoundTag original) {
        CompoundTag signalScoutTag = new CompoundTag();
        extendedSignals$advancedSignalScout.toNBT(signalScoutTag);
        original.put("signal_scout", signalScoutTag);
        return original;
    }

    @ModifyReturnValue(method = "read", at = @At("RETURN"))
    private static Train extendedSignals$read(Train original, @Local(name = "tag") CompoundTag tag) {
        AdvancedSignalScout.from(original).fromNBT(tag.getCompound("signal_scout"));
        return original;
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

    /**
     *
     * @return
     */
    @Override
    public AdvancedSignalScout advancedSignalScout() {
        return extendedSignals$advancedSignalScout;
    }

    /**
     * @return
     */
    @Override
    public boolean isManual() {
        return runtime.getSchedule() == null || runtime.paused;
    }
}

