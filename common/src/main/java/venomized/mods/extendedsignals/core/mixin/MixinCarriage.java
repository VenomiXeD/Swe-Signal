package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import venomized.mods.extendedsignals.core.create.AdvancedSignalScout;

@Mixin(value = Carriage.class)
public abstract class MixinCarriage {
    @Shadow
    public Train train;

    @ModifyArg(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/TravellingPoint;travel(Lcom/simibubi/create/content/trains/graph/TrackGraph;DLcom/simibubi/create/content/trains/entity/TravellingPoint$ITrackSelector;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IEdgePointListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$ITurnListener;Lcom/simibubi/create/content/trains/entity/TravellingPoint$IPortalListener;)D"
            ),
            index = 2
    )
    public TravellingPoint.ITrackSelector extendedSignals$listenSwitchEncounter(TravellingPoint.ITrackSelector trackSelector, @Local(name = "atFront") boolean atFront) {
        return (graph, options) -> {
            if (atFront)
                AdvancedSignalScout.from(train).onPassSwitch(options.getFirst(), options.getSecond().getFirst().getKey());
//
            return trackSelector.apply(graph, options);
        };
    }
}
