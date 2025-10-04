package venomized.mc.mods.swsignals.mixin.client;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mc.mods.swsignals.blockentity.BlockEntityTrainConfig;
import venomized.mc.mods.swsignals.client.sound.ITrainSound;

@Mixin(value = CarriageSounds.class, remap = false)
public abstract class MixinCarriageSounds {
    @Unique
    public ITrainSound swe_Signal$trainSound;
    @Shadow
    private net.createmod.catnip.animation.LerpedFloat speedFactor;
    @Shadow
    private CarriageContraptionEntity entity;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CarriageContraptionEntity entity, CallbackInfo ci) {
        entity.getContraption().presentBlockEntities.values().forEach(blockEntities -> {
            if (blockEntities instanceof BlockEntityTrainConfig beTC) {
                this.swe_Signal$trainSound = beTC.trainSound();
            }
        });
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(Carriage.DimensionalCarriageEntity dce, CallbackInfo ci) {
        if (swe_Signal$trainSound == null) {
        }
    }

    @Inject(method = "submitSharedSoundVolume", at = @At("HEAD"))
    public void submitSharedSoundVolume(Vec3 location, float volume, CallbackInfo ci) {
        if (swe_Signal$trainSound == null) {
        }
    }
}
