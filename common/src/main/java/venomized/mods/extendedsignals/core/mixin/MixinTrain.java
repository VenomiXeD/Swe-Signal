package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.create.tracks.DelayedSignalCrossTrigger;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.ITrainDoorData;
import venomized.mods.extendedsignals.core.create.tracks.ATCController;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData {
    @Unique
    private static final int TICKS_ON_CROSSED_TRIGGERING_DELAY = 20;
    @Unique
    private final List<DelayedSignalCrossTrigger> extendedSignals$delayedOnCrossedTriggering = new ReferenceArrayList<>();
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
                UUID enteringGroup = signalBoundary.getGroup(
                        couple.getSecond()
                                .getSecond()
                );
                boolean side = Objects.equals(enteringGroup, signalBoundary.groups.getFirst());
                extendedSignals$delayedOnCrossedTriggering
                        .add(
                                new DelayedSignalCrossTrigger(
                                        20,
                                        side ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                        signalBoundary
                                )
                        );
            }

            return original.test(distance, couple);
        };
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(Level level, CallbackInfo ci) {
        Iterator<DelayedSignalCrossTrigger> it = extendedSignals$delayedOnCrossedTriggering.iterator();
        while (it.hasNext()) {
            DelayedSignalCrossTrigger delayedSignalCrossTrigger = it.next();
            if (delayedSignalCrossTrigger.getRemainingDelayTicks() <= 0) {
                ((IExtendedSignalBoundary) delayedSignalCrossTrigger.getSignalBoundary())
                        .extendedSignal$onCrossed(delayedSignalCrossTrigger.getDirection(), (Train) (Object) this);
                it.remove();
                continue;
            }

            delayedSignalCrossTrigger.setRemainingDelayTicks(
                    delayedSignalCrossTrigger.getRemainingDelayTicks() - 1
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

