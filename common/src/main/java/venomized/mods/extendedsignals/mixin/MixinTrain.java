package venomized.mods.extendedsignals.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.createmod.catnip.data.Pair;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.create.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.create.ITrainDoorData;
import venomized.mods.extendedsignals.create.tracks.ATCController;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData {
    @Unique
    private static final int TICKS_ON_CROSSED_TRIGGERING_DELAY = 20;
    @Unique
    private final List<Pair<Integer, SignalBoundary>> extendedSignals$delayedOnCrossedTriggering = new ReferenceArrayList<>();
    @Unique
    private boolean extendedSignals$doorOpen = false;

    @ModifyReturnValue(method = "frontSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener frontSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            if (trackEdgePoint instanceof ATCController atcController) {
                atcController.onATCAction(((Train) (Object) this));
            }

            if (trackEdgePoint instanceof SignalBoundary signalBoundary) {
                extendedSignals$delayedOnCrossedTriggering
                        .add(Pair.of(TICKS_ON_CROSSED_TRIGGERING_DELAY, signalBoundary));
            }

            return original.test(distance, couple);
        };
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(Level level, CallbackInfo ci) {
        Iterator<Pair<Integer, SignalBoundary>> it = extendedSignals$delayedOnCrossedTriggering.iterator();
        while (it.hasNext()) {
            Pair<Integer, SignalBoundary> remainingTicksDelay_signalBoundaryToUpdate = it.next();
            if (remainingTicksDelay_signalBoundaryToUpdate.getFirst() <= 0) {
                ((IExtendedSignalBoundary) remainingTicksDelay_signalBoundaryToUpdate.getSecond())
                        .extendedSignal$onCrossed((Train) (Object) this);
                it.remove();
                // ExtendedSignalsCore.LOGGER.info("Removed delayed onCross event trigger from collection");
                continue;
            }
            remainingTicksDelay_signalBoundaryToUpdate.setFirst(
                    remainingTicksDelay_signalBoundaryToUpdate.getFirst() - 1
            );
        }
    }

    @Inject(method = "occupy", at = @At("HEAD"))
    private void onOccupy(UUID groupId, UUID boundaryId, CallbackInfoReturnable<Boolean> cir) {
        // SignalNetwork.onSignalUpdate(groupId, boundaryId);
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

