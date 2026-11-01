package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TravellingPoint.class)
public abstract class MixinTravellingPoint {
    @Inject(method = "steer", at = @At("RETURN"))
    public void extendedSignals$guardNullSteerDirection(TravellingPoint.SteerDirection direction, Vec3 upNormal, CallbackInfoReturnable<TravellingPoint.ITrackSelector> cir, @Local(name = "direction") LocalRef<TravellingPoint.SteerDirection> directionRef) {
        if (direction == null)
            directionRef.set(TravellingPoint.SteerDirection.NONE);
    }
}
