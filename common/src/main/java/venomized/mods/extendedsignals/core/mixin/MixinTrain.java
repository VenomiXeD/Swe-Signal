package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.create.ITrainDoorData;
import venomized.mods.extendedsignals.core.create.tracks.ATCController;
import venomized.mods.extendedsignals.core.create.tracks.DelayedSignalCrossTrigger;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigationAccessor;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData {
    @Unique
    private static final int TICKS_ON_CROSSED_TRIGGERING_DELAY = 20;
    @Unique
    private final List<DelayedSignalCrossTrigger> extendedSignals$delayedOnCrossedTriggering = new ReferenceArrayList<>();
    @Shadow
    public ScheduleRuntime runtime;
    @Shadow
    public Navigation navigation;
    @Shadow
    public TrackGraph graph;
    @Unique
    private boolean extendedSignals$doorOpen = false;

    @ModifyReturnValue(method = "frontSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener frontSignalListener(TravellingPoint.IEdgePointListener original) {
        return (distance, couple) -> {
            TrackEdgePoint trackEdgePoint = couple.getFirst();
            boolean front = trackEdgePoint.isPrimary(couple.getSecond()
                    .getSecond());// Objects.equals(enteringGroup, signalBoundary.groups.getFirst());


            if (trackEdgePoint instanceof ATCController atcController) {
                atcController.onATCAction(((Train) (Object) this));
                return false;
            }

            if (trackEdgePoint instanceof IExtendedSignalBoundary<?> signalBoundary) {
                extendedSignals$delayedOnCrossedTriggering.add(
                        new DelayedSignalCrossTrigger(TICKS_ON_CROSSED_TRIGGERING_DELAY, front, signalBoundary)
                        );


                if (trackEdgePoint instanceof TrackEdgePointSignalModifier<?> modifier && navigation != null) {
                    if (modifier.isAligned(modifier.isPrimary(couple.getSecond().getSecond()))) {
                        if (modifier.shouldApply()) {
                            ((INavigationAccessor) navigation).extendedSignals$activeModifiers()
                                    .put(modifier.getType().getId(), modifier);
                        } else {
                            ((INavigationAccessor) navigation).extendedSignals$activeModifiers()
                                    .remove(modifier.getType().getId());
                        }
                    }
                }
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
                delayedSignalCrossTrigger.getSignalBoundary()
                        .onSignalCrossed(delayedSignalCrossTrigger.isPrimary(), (Train) (Object) this);
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

