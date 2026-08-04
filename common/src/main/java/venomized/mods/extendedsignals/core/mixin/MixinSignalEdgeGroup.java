package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.create.tracks.InterlockingManager;

@Mixin(value = SignalEdgeGroup.class, remap = false)
public abstract class MixinSignalEdgeGroup {
    @Inject(method = "isOccupiedUnless(Lcom/simibubi/create/content/trains/entity/Train;)Z", at = @At("HEAD"), cancellable = true)
    public void extendedSignals$isThisOwnedAlready(Train train, CallbackInfoReturnable<Boolean> cir) {
        if (InterlockingManager.trainOwnsGroupIntersecting(train, (SignalEdgeGroup) (Object) this) == InterlockingManager.ReservationResult.CONFLICT)
            cir.setReturnValue(true);
    }
}
