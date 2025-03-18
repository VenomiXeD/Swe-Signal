package venomized.mc.mods.swsignals.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mc.mods.swsignals.create.tracks.ATCController;


@Mixin(value = Train.class, remap = false)
public class MixinTrain {
	@Inject(method = "frontSignalListener", at = @At("RETURN"), cancellable = true)
	private void frontSignalListener(CallbackInfoReturnable<TravellingPoint.IEdgePointListener> cir) {
		Train self = (Train) (Object) this;
		TravellingPoint.IEdgePointListener original = cir.getReturnValue();

		TravellingPoint.IEdgePointListener modified = (distance, couple) -> {
			if(couple.getFirst() instanceof ATCController atcController) {
				atcController.onATCAction(self);
				return false;
			}

			return original.test(distance, couple);
		};

		cir.setReturnValue(modified);
	}
}
