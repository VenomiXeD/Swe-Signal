package venomized.mc.mods.swsignals.client.sound;

import lombok.Setter;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class LoopingSound extends AbstractTickableSoundInstance {
    @Setter
    private float x, y, z;
    @Setter
    private float volume;

    public LoopingSound(SoundEvent p_235076_, SoundSource p_235077_, RandomSource p_235078_) {
        super(p_235076_, p_235077_, p_235078_);
        this.looping = true;
        this.attenuation = Attenuation.LINEAR;
        this.relative = true;

        volume = 1.0f;
    }

    /**
     * @return
     */
    @Override
    public float getVolume() {
        return volume;
    }

    /**
     * @return
     */
    @Override
    public double getZ() {
        return z;
    }

    /**
     * @return
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * @return
     */
    @Override
    public double getX() {
        return x;
    }


    /**
     *
     */
    @Override
    public void tick() {

    }
}
