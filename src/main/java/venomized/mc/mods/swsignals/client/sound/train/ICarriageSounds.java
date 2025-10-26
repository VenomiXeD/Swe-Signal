package venomized.mc.mods.swsignals.client.sound.train;

import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.world.entity.Entity;

public interface ICarriageSounds {
    LerpedFloat getSpeedFactor();
    int getTick();
    int getPrevTick();
    LerpedFloat getDistanceFactor();
    Entity getEntity();
    LerpedFloat getApproachFactor();
}
