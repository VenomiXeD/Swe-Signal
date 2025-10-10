package venomized.mc.mods.swsignals.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mc.mods.swsignals.create.tracks.ATCController;

import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public abstract class MixinTrain {
    @ModifyReturnValue(method = "frontSignalListener", at = @At("RETURN"))
    public TravellingPoint.IEdgePointListener frontSignalListener(TravellingPoint.IEdgePointListener original) {
        TravellingPoint.IEdgePointListener wrapped = (distance, couple) -> {
            if (couple.getFirst() instanceof ATCController atcController) {
                atcController.onATCAction(((Train) (Object) this));
                return false;
            }
            return original.test(distance, couple);
        };

        return wrapped;
    }

    @Inject(method = "occupy", at = @At("HEAD"))
    private void onOccupy(UUID groupId, UUID boundaryId, CallbackInfoReturnable<Boolean> cir) {
        // SignalNetwork.onSignalUpdate(groupId, boundaryId);
    }
}

