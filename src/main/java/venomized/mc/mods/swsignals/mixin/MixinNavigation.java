package venomized.mc.mods.swsignals.mixin;

import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Pair;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import venomized.mc.mods.swsignals.core.SwSignal;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements TravellingPoint.IEdgePointListener {
    @Shadow
    public Train train;

    @Shadow
    public abstract TravellingPoint.ITrackSelector controlSignalScout();

    @Unique
    TravellingPoint swe_signal$signalTrigger;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onConstruct(Train train, CallbackInfo ci) {
        swe_signal$signalTrigger = new TravellingPoint();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/TravellingPoint;travel(Lcom/simibubi/create/content/trains/graph/TrackGraph;DLcom/simibubi/create/content/trains/entity/TravellingPoint$ITrackSelector;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IEdgePointListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$ITurnListener;)D"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onTick(Level level,
                       CallbackInfo ci,
                       double acceleration,
                       double brakingDistance,
                       double speedMod,
                       double preDepartureLookAhead,
                       double distanceToNextCurve,
                       TravellingPoint leadingPoint,
                       MutableDouble curveDistanceTracker,
                       double brakingDistanceNoFlicker,
                       double scanDistance,
                       MutableDouble crossSignalDistanceTracker,
                       MutableObject trackingCrossSignal
    ) {
        swe_signal$signalTrigger.node1 = leadingPoint.node1;
        swe_signal$signalTrigger.node2 = leadingPoint.node2;
        swe_signal$signalTrigger.edge = leadingPoint.edge;
        swe_signal$signalTrigger.position = leadingPoint.position;

        swe_signal$signalTrigger.travel(train.graph, 1, controlSignalScout(), this);
    }

    /**
     * @param distance                 the first input argument
     * @param trackEdgePointCouplePair the second input argument
     * @return
     */
    @Override
    public boolean test(Double distance, Pair<TrackEdgePoint, Couple<TrackNode>> trackEdgePointCouplePair) {
        TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst();

        SwSignal.LOGGER.info("Edge point hit: {}", trackEdgePoint.getClass().getName());

        return true;
    }
}
