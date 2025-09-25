package venomized.mc.mods.swsignals.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class LoopingSound extends AbstractTickableSoundInstance {
    public int t = 0;


    /**
     * @return
     */
    @Override
    public float getVolume() {
        return super.getVolume();
    }

    public LoopingSound(SoundEvent p_235076_, SoundSource p_235077_, RandomSource p_235078_) {
        super(p_235076_, p_235077_, p_235078_);
        this.looping = true;
        this.attenuation = Attenuation.NONE;
    }

    @Override
    public void tick() {
        t++;
        this.pitch = 0.5f + Mth.sin(t/20f * 0.01f * Mth.PI)/2f;
    }
}
