package venomized.mods.extendedsignals.core.mixin;

import com.simibubi.create.content.contraptions.actors.roller.RollerMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;

@Mixin(value = com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour.class, remap = false)
public abstract class MixinBlockBreakingMovementBehaviour {
    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    public void extendedSignals$infiniteBlockBreakSpeed(MovementContext context, CallbackInfoReturnable<Float> cir) {
        if (!ExtendedSignalsConfig.SERVER.creativeBlockBreakingMovementBehavior.getAsBoolean())
            return;

        context.stall = false;
        cir.setReturnValue(Float.MAX_VALUE);
    }
}
