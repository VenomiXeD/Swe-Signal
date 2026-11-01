package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.simibubi.create.content.trains.entity.TravellingPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TravellingPoint.class)
public abstract class MixinTravellingPoint {
    @ModifyVariable(method = "steer", at = @At("HEAD"), argsOnly = true)
    public TravellingPoint.SteerDirection extendedSignals$guardNullSteerDirection(TravellingPoint.SteerDirection arg0) {
        // arg0 for some reason may be null, this procedure should prevent the crash
        return arg0 == null ? TravellingPoint.SteerDirection.NONE : arg0;
    }
}
