package venomized.mods.extendedsignals.client.sound;

import lombok.Setter;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class LoopingSound extends AbstractTickableSoundInstance {
    private double x, y, z;
    @Setter
    private float volume;
    @Setter
    private float pitch;

    public LoopingSound(SoundEvent p_235076_, SoundSource p_235077_, RandomSource p_235078_) {
        super(p_235076_, p_235077_, p_235078_);
        this.looping = true;
        this.attenuation = Attenuation.LINEAR;

        volume = 1.0f;
    }

    /**
     * @return
     */
    @Override
    public float getVolume() {
        return volume;
    }


    public void setLocation(Vec3 pos) {
        this.x = pos.x();
        this.y = pos.y();
        this.z = pos.z();
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

    /**
     * @return
     */
    @Override
    public float getPitch() {
        return pitch;
    }

    public void cleanup() {
        this.stop();
    }

    @Override
    public boolean isStopped() {
        return super.isStopped();
    }
}
