package venomized.mods.swsignal.client.sound.train;

import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.world.entity.Entity;
import venomized.mc.mods.swsignals.client.sound.train.TrainSound;

import java.util.Optional;

public interface ICarriageSounds {
    LerpedFloat getSpeedFactor();

    int getTick();

    int getPrevTick();

    LerpedFloat getDistanceFactor();

    Entity getEntity();

    LerpedFloat getApproachFactor();

    Optional<TrainSound> getCustomTrainSound();
}
