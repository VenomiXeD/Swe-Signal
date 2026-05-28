package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.schedule.ScheduleRuntime;
import com.simibubi.create.content.trains.signal.SignalBlock;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
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
import venomized.mods.extendedsignals.core.create.ITrainDoorData;
import venomized.mods.extendedsignals.core.create.tracks.ATCController;
import venomized.mods.extendedsignals.core.create.tracks.DelayedSignalCrossTrigger;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.TrackEdgePointSignalModifier;
import venomized.mods.extendedsignals.core.mixin_interfaces.INavigationAccessor;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Iterator;
import java.util.List;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain implements ITrainDoorData, ITrain {
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
    @Shadow
    public List<Carriage> carriages;
    @Unique
    private boolean extendedSignals$doorOpen = false;
    @Unique
    private final int extendedSignals$shuntRequestCooldown = 0;

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
                (a, b) -> {
                    TrackEdgePoint point = b.getFirst();

                    boolean primary = point.isPrimary(b.getSecond().getSecond());

                    if (point instanceof SignalBoundary signalBoundary) {
                        @SuppressWarnings("unchecked")
                        IExtendedSignalBoundary<SignalBoundary> boundary = (IExtendedSignalBoundary<SignalBoundary>) signalBoundary;
                        boundary.onSignalScout(
                                primary ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                                new SignalStateNode().setProceed(true), ((Train) (Object) this)
                        );

                        shuntRequest.requester().sendSystemMessage(
                                Component.translatable("")
                        );

                        return true;
                    }

                    return false;
                }
        );
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(Level level, CallbackInfo ci) {


        Iterator<DelayedSignalCrossTrigger> it = extendedSignals$delayedOnCrossedTriggering.iterator();
        while (it.hasNext()) {
            DelayedSignalCrossTrigger delayedSignalCrossTrigger = it.next();
            if (delayedSignalCrossTrigger.getRemainingDelayTicks() <= 0) {
                delayedSignalCrossTrigger.getSignalBoundary()
                        .onSignalCrossed(
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

