package venomized.mc.mods.swsignals.mixin.client;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.blockentity.BlockEntityTrainConfig;
import venomized.mc.mods.swsignals.client.sound.ITrainSound;
import venomized.mc.mods.swsignals.client.sound.LoopingSound;

@Mixin(value = CarriageSounds.class, remap = false)
public abstract class MixinCarriageSounds {
    @Unique
    public ITrainSound swe_Signal$trainSound;
    @Shadow
    private net.createmod.catnip.animation.LerpedFloat speedFactor;
    @Shadow
    private CarriageContraptionEntity entity;

    @Unique
    private LoopingSound swe_Signal$loopingSound;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CarriageContraptionEntity dce, CallbackInfo ci) {
        dce.getContraption().presentBlockEntities.values().forEach(blockEntities -> {
            if (blockEntities instanceof BlockEntityTrainConfig beTC) {
                this.swe_Signal$trainSound = beTC.trainSound();
            }
        });

        if (swe_Signal$trainSound != null) {
            swe_Signal$trainSound.init((CarriageSounds)(Object)this);
        }

        swe_Signal$loopingSound = new LoopingSound(
                AllSounds.TRAIN_VVVF.get(),
                SoundSource.NEUTRAL,
                dce.level().random
        );
        Minecraft.getInstance().getSoundManager().play(swe_Signal$loopingSound);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(Carriage.DimensionalCarriageEntity dce, CallbackInfo ci) {
        if (swe_Signal$trainSound == null) {
            return;
        }
        swe_Signal$trainSound.tick(dce);
    }

    @Inject(method = "submitSharedSoundVolume", at = @At("HEAD"))
    public void submitSharedSoundVolume(Vec3 location, float volume, CallbackInfo ci) {

        swe_Signal$loopingSound.setPos(location.toVector3f());
        swe_Signal$loopingSound.setVolume(speedFactor.getValue());

        swe_Signal$loopingSound.setPitch(1f + swe_Signal$vvvfSawPitch(speedFactor.getValue(),10));
        if (swe_Signal$trainSound == null) {
            return;
        }
        swe_Signal$trainSound.submitSharedSoundVolume(entity);
    }

    @Unique
    private static float swe_Signal$vvvfSawPitch(float x, int cycles) {
        if (x < 0) x = 0;
        if (x > 1) x = 1;

        double decayPower = 2.0; // higher = faster decay of oscillations

        // fractional part of (x * cycles) — generates sawtooth wave
        double saw = (x * cycles) - Math.floor(x * cycles);

        // decaying amplitude factor
        double envelope = Math.pow(1 - x, decayPower);

        // combine ramp + decay + base rise
        double result = (saw * envelope) + (x * (1 - envelope));

        // clamp to [0, 1]
        return (float)Math.max(0, Math.min(1, result));
    }
}
