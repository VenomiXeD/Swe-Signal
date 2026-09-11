package venomized.mods.extendedsignals.core.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import venomized.mods.extendedsignals.core.block.ITrainSoundModifierBlock;
import venomized.mods.extendedsignals.core.client.MiscTrainDataClientSync;
import venomized.mods.extendedsignals.core.client.sound.train.ICarriageSounds;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;
import venomized.mods.extendedsignals.core.util.IEntityMotionData;

import java.util.Optional;

@Mixin(value = CarriageSounds.class, remap = false)
public abstract class MixinCarriageSounds implements ICarriageSounds, IEntityMotionData {
    @Unique
    TrainSound extendedSignals$trainSound;
    @Shadow
    LerpedFloat speedFactor;
    @Shadow
    CarriageContraptionEntity entity;

    @Shadow
    int tick;

    @Shadow
    LerpedFloat distanceFactor;

    @Shadow
    LerpedFloat approachFactor;

    @Shadow
    int prevSharedTick;

    @Unique
    Vec3 contraptionMotion = Vec3.ZERO;

    // @Unique
    // private Vec3 combinedMotion = Vec3.ZERO;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(CarriageContraptionEntity dce, CallbackInfo ci) {
        // TODO: contraption thingamabob vanished so no sound for now
        Optional<Block> first = dce.getContraption().getBlocks().values().stream().map(e -> e.state().getBlock()).filter(e -> e instanceof ITrainSoundModifierBlock).findFirst();
        if (first.isPresent() && first.get() instanceof ITrainSoundModifierBlock soundModifierBlock) {
            extendedSignals$trainSound = soundModifierBlock.constructTrainSound();
            extendedSignals$trainSound.init(this, dce);
        }
        // swe_Signal$trainSound.ifPresent(trainSound -> trainSound.init(this, dce));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/entity/CarriageContraptionEntity;getCarriage()Lcom/simibubi/create/content/trains/entity/Carriage;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onTick_updateMotion(Carriage.DimensionalCarriageEntity dce, CallbackInfo ci, Minecraft mc, Entity camEntity, Vec3 leadingAnchor, Vec3 trailingAnchor, Vec3 cam, Vec3 contraptionMotion, Vec3 combinedMotion) {
        this.contraptionMotion = contraptionMotion;
        // this.combinedMotion = combinedMotion;
    }

    @Inject(method = "tick", at = @At("HEAD"))  
    public void onTick(Carriage.DimensionalCarriageEntity dce, CallbackInfo ci) {
        // swe_Signal$trainSound.ifPresent(e -> e.tick(dce));
        if (extendedSignals$trainSound != null)
            extendedSignals$trainSound.tick(dce);
    }

    // @ModifyExpressionValue(method = "submitSharedSoundVolume",at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/entity/CarriageSounds$LoopingSound;getVolume()F"))
    // public float extendedSignals$neverCancelSound(float original) {
    //     if (extendedSignals$trainSound != null)
    //         return Float.MIN_VALUE;
    //     return original;
    // }

    @Inject(method = "submitSharedSoundVolume", at = @At("HEAD"))
    public void onSubmitSharedSoundVolume(Vec3 location, float volume, CallbackInfo ci) {
        if (extendedSignals$trainSound != null)
            extendedSignals$trainSound.submitSharedSoundVolume(location, entity, MiscTrainDataClientSync.getTrainData(entity.trainId));
        // swe_Signal$trainSound.ifPresent(trainSound -> trainSound.submitSharedSoundVolume(location, volume, entity));
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void onStop(CallbackInfo ci) {
        if (extendedSignals$trainSound != null)
            extendedSignals$trainSound.destroySounds();
        extendedSignals$trainSound = null;
        //swe_Signal$trainSound.ifPresent(TrainSound::destroySounds);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/AllSoundEvents$SoundEntry;playAt(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;FFZ)V"), remap = false)
    private void extendedSignals$disableSteam(AllSoundEvents.SoundEntry instance, Level world, Vec3 pos, float volume, float pitch, boolean fade, Operation<Void> original) {
        if (instance == AllSoundEvents.STEAM && extendedSignals$trainSound != null)
            return;
        original.call(instance, world, pos, volume, pitch, fade);
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

    /**
     * @return
     */
    @Override
    public Optional<TrainSound> getCustomTrainSound() {
        return Optional.empty();
    }

    /**
     * @return
     */
    @Override
    public Vec3 getMotion() {
        return this.contraptionMotion;
    }
}