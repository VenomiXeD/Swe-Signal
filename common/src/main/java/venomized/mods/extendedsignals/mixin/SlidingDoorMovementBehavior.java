package venomized.mods.extendedsignals.mixin;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.decoration.slidingDoor.SlidingDoorMovementBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.create.ITrainDoorData;

@Mixin(value = SlidingDoorMovementBehaviour.class, remap = false)
public abstract class SlidingDoorMovementBehavior {
    @Inject(method = "shouldOpen", at = @At("RETURN"), cancellable = true)
    public void onShouldOpen(MovementContext context, CallbackInfoReturnable<Boolean> cir) {
        boolean isForcedClosed = false;

        if (context.contraption.entity instanceof CarriageContraptionEntity cce) {
            Train train = Create.RAILWAYS.sided(context.world)
                    .trains.get(cce.trainId);

            if (train != null)
                isForcedClosed = ((ITrainDoorData) train).swe_Signal$doorForcedClosed();

        }

        cir.setReturnValue(cir.getReturnValue() && !isForcedClosed);
    }
}
