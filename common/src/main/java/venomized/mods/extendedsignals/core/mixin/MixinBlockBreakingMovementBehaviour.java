package venomized.mods.extendedsignals.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;

@Mixin(value = com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour.class, remap = false)
public abstract class MixinBlockBreakingMovementBehaviour {
    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    public void extendedSignals$infiniteBlockBreakSpeed(MovementContext context, CallbackInfoReturnable<Float> cir) {
        if (ExtendedSignalsConfig.SERVER.creativeBlockBreakingMovementBehavior.isFalse())
            return;

        context.stall = false;
        cir.setReturnValue(Float.MAX_VALUE);
    }

    @WrapOperation(method = "onBlockBroken", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;stall:Z", opcode = Opcodes.PUTFIELD))
    public void extendedSignals$preventBlockBreakStall(MovementContext instance, boolean value, Operation<Void> original) {
        if (value && ExtendedSignalsConfig.SERVER.creativeBlockBreakingMovementBehavior.isTrue())
            return;

        original.call(instance, value);
    }

    @WrapOperation(method = "tick", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;stall:Z", opcode = Opcodes.PUTFIELD))
    public void extendedSignals$preventTickStall(MovementContext instance, boolean value, Operation<Void> original) {
        if (value && ExtendedSignalsConfig.SERVER.creativeBlockBreakingMovementBehavior.isTrue())
            return;

        original.call(instance, value);
    }

    @WrapOperation(method = "visitNewPosition", at = @At(value = "FIELD", target = "Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;stall:Z", opcode = Opcodes.PUTFIELD))
    public void extendedSignals$preventVisitNewPositionStall(MovementContext instance, boolean value, Operation<Void> original) {
        if (value && ExtendedSignalsConfig.SERVER.creativeBlockBreakingMovementBehavior.isTrue())
            return;

        original.call(instance, value);
    }
}
