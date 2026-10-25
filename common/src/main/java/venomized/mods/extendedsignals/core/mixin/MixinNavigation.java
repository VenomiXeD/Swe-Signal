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
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.lang.ref.WeakReference;
import java.util.*;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation {
    @Shadow
    public Train train;
    @Shadow
    public Pair<UUID, Boolean> waitingForSignal;
    @Shadow
    private Map<UUID, Pair<SignalBoundary, Boolean>> waitingForChainedGroups;

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

    @Inject(method = "startNavigation", at = @At("HEAD"))
    public void extendedSignals$onStartNavigation(DiscoveredPath pathTo, CallbackInfoReturnable<Double> cir) {
        ((ITrain) train).getAdvancedSignalScout().setSignalScoutCooldown(0);
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
        if ((waitingForSignal != null && train.speed == 0) || train.graph == null) {
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
}
