package venomized.mods.extendedsignals.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Navigation;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import com.simibubi.create.content.trains.graph.TrackEdge;
import com.simibubi.create.content.trains.graph.TrackNode;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;
import org.joml.Math;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import venomized.mods.extendedsignals.create.IExtendedSignalBoundary;

import java.util.UUID;

@Mixin(value = Navigation.class, remap = false)
public abstract class MixinNavigation implements TravellingPoint.IEdgePointListener, TravellingPoint.ITurnListener {
    @Unique
    private static final double LOOK_AHEAD_DISTANCE = 128;

    @Shadow
    public Train train;
    @Unique
    private TravellingPoint swe_signal$signalTrigger;

    @Shadow
    public abstract TravellingPoint.ITrackSelector controlSignalScout();

    @Shadow
    public double distanceToDestination;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onCtor(Train train, CallbackInfo ci) {
        swe_signal$signalTrigger = new TravellingPoint();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/TravellingPoint;travel(Lcom/simibubi/create/content/trains/graph/TrackGraph;DLcom/simibubi/create/content/trains/entity/TravellingPoint$ITrackSelector;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IEdgePointListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$ITurnListener;)D"
            )
    )
    public void onTick(Level level,
                       CallbackInfo ci,
                       @Local(name = "speedMod") double speedMod,
                       @Local(name = "leadingPoint") TravellingPoint leadingPoint
    ) {
        swe_signal$signalTrigger.node1 = leadingPoint.node1;
        swe_signal$signalTrigger.node2 = leadingPoint.node2;
        swe_signal$signalTrigger.edge = leadingPoint.edge;
        swe_signal$signalTrigger.position = leadingPoint.position;

        final double lookaheadDistance = Math.min(LOOK_AHEAD_DISTANCE, distanceToDestination);

        swe_signal$signalTrigger.travel(
                train.graph,
                lookaheadDistance * speedMod,
                controlSignalScout(),
                this,
                this
        );
    }

    /**
     * @param distance                 the first input argument
     * @param trackEdgePointCouplePair the second input argument
     * @return
     */
    @Override
    public boolean test(Double distance, Pair<TrackEdgePoint, Couple<TrackNode>> trackEdgePointCouplePair) {
        TrackEdgePoint trackEdgePoint = trackEdgePointCouplePair.getFirst() ;
        if (!(trackEdgePoint instanceof SignalBoundary signalBoundary))
            return false;


        UUID enteringGroup = signalBoundary.getGroup(
                trackEdgePointCouplePair.getSecond().getSecond()
        );
        boolean side = enteringGroup.equals(signalBoundary.groups.getFirst());
        if (signalBoundary.cachedStates.get(side) == SignalBlockEntity.SignalState.RED)
            return true;

        ((IExtendedSignalBoundary) signalBoundary).extendedSignal$onScout(
                side ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE, this.train
        );


        return false;
    }

    /**
     * @param aDouble   the first input argument
     * @param trackEdge the second input argument
     */
    @Override
    public void accept(Double aDouble, TrackEdge trackEdge) {
    }
}
