package venomized.mc.mods.swsignals.mixin.client;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomized.mc.mods.swsignals.blockentity.BlockEntityTrainConfig;
import venomized.mc.mods.swsignals.client.sound.train.ICarriageSounds;
import venomized.mc.mods.swsignals.client.sound.train.TrainSound;

@Mixin(value = CarriageSounds.class, remap = false)
public abstract class MixinCarriageSounds implements ICarriageSounds {
    @Unique
    public TrainSound swe_Signal$trainSound;
    @Shadow
    private net.createmod.catnip.animation.LerpedFloat speedFactor;
    @Shadow
    private CarriageContraptionEntity entity;

    @Shadow
    private int tick;

    @Shadow
    private LerpedFloat distanceFactor;

    @Shadow
    private LerpedFloat approachFactor;

    @Shadow private int prevSharedTick;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CarriageContraptionEntity dce, CallbackInfo ci) {
        dce.getContraption().presentBlockEntities.values().forEach(blockEntities -> {
            if (blockEntities instanceof BlockEntityTrainConfig beTC) {
                this.swe_Signal$trainSound = beTC.trainSound();
            }
        });

        if (swe_Signal$trainSound != null) {
            swe_Signal$trainSound.init(this, dce);
        }
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
        if (swe_Signal$trainSound != null)
            swe_Signal$trainSound.submitSharedSoundVolume(location, volume, this.entity);
    }

    /**
     * @return
     */
    @Override
    public LerpedFloat getSpeedFactor() {
        return this.speedFactor;
    }

    /**
     * @return
     */
    @Override
    public int getTick() {
        return this.tick;
    }

    /**
     * @return
     */
    @Override
    public int getPrevTick() {
        return this.prevSharedTick;
    }

    /**
     * @return
     */
    @Override
    public LerpedFloat getDistanceFactor() {
        return this.distanceFactor;
    }

    /**
     * @return
     */
    @Override
    public Entity getEntity() {
        return this.entity;
    }

    /**
     * @return
     */
    @Override
    public LerpedFloat getApproachFactor() {
        return this.approachFactor;
    }
}