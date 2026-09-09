package venomized.mods.extendedsignals.core.mixin.client;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import venomized.mods.extendedsignals.core.client.sound.IPitchSound;

@Mixin(SoundEngine.class)
public abstract class MixinSoundEngine {
    @Inject(method = "calculatePitch", at = @At("HEAD"), cancellable = true)
    public void extendedSignals$allowAnyPitch(SoundInstance sound, CallbackInfoReturnable<Float> cir) {
        if (sound instanceof IPitchSound) {
            cir.setReturnValue(sound.getPitch());
        }
    }
}
