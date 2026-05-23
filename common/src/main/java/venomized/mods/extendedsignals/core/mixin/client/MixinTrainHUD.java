package venomized.mods.extendedsignals.core.mixin.client;

import com.simibubi.create.content.trains.TrainHUD;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(value = TrainHUD.class, remap = false)
public abstract class MixinTrainHUD {
    @Inject(method = "tick", at = @At("RETURN"))
    private static void tick(CallbackInfo ci) {
        // ATCOverlayHUD.tick();
    }
}
