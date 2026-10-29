package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.DiscoveredPath;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import net.createmod.catnip.data.Pair;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;
import venomized.mods.extendedsignals.core.create.NavigationDistanceValuesSnapshot;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigation;

import java.util.Map;
import java.util.UUID;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements INavigation {
    @Unique
    private NavigationDistanceValuesSnapshot extendedSignals$navigationDistanceValuesSnapshot;

    @Unique
    private double extendedSignals$capturedSpeedMod;

    @Unique
    private TrackNode extendedSignals$startScoutNode1;
    @Unique
    private TrackNode extendedSignals$startScoutNode2;
    @Unique
    private TrackEdge extendedSignals$startScoutEdge;
    @Unique
    private double extendedSignals$startScoutPosition;

    /**
     * @return
     */
    @Override
    public TrackNode extendedSignals$startScoutNode1() {
        return extendedSignals$startScoutNode1;
    }

    /**
     * @return
     */
    @Override
    public TrackNode extendedSignals$startScoutNode2() {
        return extendedSignals$startScoutNode2;
    }

    /**
     * @return
     */
    @Override
    public TrackEdge extendedSignals$startScoutEdge() {
        return extendedSignals$startScoutEdge;
    }

    /**
     * @return
     */
    @Override
    public double extendedSignals$startScoutPosition() {
        return extendedSignals$startScoutPosition;
    }

    /**
     *
     * @return
     */
    @Override
    public double extendedSignals$speedMod() {
        return extendedSignals$capturedSpeedMod;
    }

    /**
     * @return
     */
    @Override
    public @NotNull NavigationDistanceValuesSnapshot extendedSignals$postSignalScoutCapturedValues() {
        return extendedSignals$navigationDistanceValuesSnapshot;
    }

    @Shadow
    public Train train;
    @Shadow
    public Pair<UUID, Boolean> waitingForSignal;
    @Shadow
    private Map<UUID, Pair<SignalBoundary, Boolean>> waitingForChainedGroups;

    @Shadow
    public double distanceToSignal;

    @Shadow
    public double distanceToDestination;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void extendedSignals$onCtor(Train train, CallbackInfo ci) {
        extendedSignals$navigationDistanceValuesSnapshot = new NavigationDistanceValuesSnapshot(0, 0, null);
    }

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
        return AdvancedSignalScout.from(train).bypassesRedSignals() ? 0 : ExtendedSignalsConfig.SERVER.defaultMinScanDistance.get();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/TravellingPoint;travel(Lcom/simibubi/create/content/trains/graph/TrackGraph;DLcom/simibubi/create/content/trains/entity/TravellingPoint$ITrackSelector;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IEdgePointListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$ITurnListener;)D",
                    shift = At.Shift.AFTER)
    )
    public void extendedSignals$captureVariables(Level level, CallbackInfo ci, @Local(name = "speedMod") double speedMod, @Local(name = "leadingPoint") TravellingPoint leadingPoint) {
        extendedSignals$capturedSpeedMod = speedMod;

        extendedSignals$startScoutNode1 = leadingPoint.node1;
        extendedSignals$startScoutNode2 = leadingPoint.node2;
        extendedSignals$startScoutEdge = leadingPoint.edge;
        extendedSignals$startScoutPosition = leadingPoint.position;

        extendedSignals$navigationDistanceValuesSnapshot = new NavigationDistanceValuesSnapshot(distanceToDestination, distanceToSignal, waitingForSignal == null ? null : waitingForSignal.copy());
    }

    @Inject(method = "startNavigation", at = @At("HEAD"))
    public void extendedSignals$onStartNavigation(DiscoveredPath pathTo, CallbackInfoReturnable<Double> cir) {
        AdvancedSignalScout.from(train).setSignalScoutCooldown(0);
    }

    @Inject(method = "reserveChain", at = @At("HEAD"))
    public void extendedSignals$persistChainReservation(CallbackInfo ci) {
        InterlockingManager.tryReserveChain(train, waitingForChainedGroups);
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
        if (train.graph == null || train.speed == 0) {
            InterlockingManager.clearReservationsForTrain(train);
            return;
        }

        InterlockingManager.groupOwnerships.entrySet().stream().filter(e -> e.getValue().train().equals(train.id))
                .forEach(entry -> {
                    SignalEdgeGroup signalEdgeGroup = Create.RAILWAYS.signalEdgeGroups.get(entry.getKey());
                    if (signalEdgeGroup == null)
                        return;
//
                    if (signalEdgeGroup.reserved == null)
                        signalEdgeGroup.reserved = entry.getValue().boundary();

                    train.reservedSignalBlocks.add(entry.getKey());
                });
    }

    @Inject(method = "cancelNavigation", at = @At("HEAD"))
    public void extendedSignals$navigationCancelled(CallbackInfo ci) {
        InterlockingManager.clearReservationsForTrain(train);
    }

    @Definition(id = "targetDistance", local = @Local(type = double.class, name = "targetDistance"))
    @Expression("targetDistance")
    @Inject(method = "tick", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 0))
    public void extendedSignals$tick(Level level, CallbackInfo ci, @Local(name = "targetDistance") LocalDoubleRef targetDistance) {
        if (waitingForSignal != null && AdvancedSignalScout.from(train).bypassesRedSignals()) {
            targetDistance.set(distanceToDestination);
        }
    }

}
