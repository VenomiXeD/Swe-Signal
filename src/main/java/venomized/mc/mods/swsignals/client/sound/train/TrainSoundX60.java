package venomized.mc.mods.swsignals.client.sound.train;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.client.sound.LoopingSound;

public class TrainSoundX60 extends TrainSound {
    private LoopingSound vvvfSound;
    /**
     * @param location
     * @param volume
     * @param entity
     */
    @Override
    public void submitSharedSoundVolume(Vec3 location, float volume, CarriageContraptionEntity entity) {
        vvvfSound.setPos(entity.position().toVector3f());
        vvvfSound.setVolume(getCarriageSounds().getSpeedFactor().getValue());

        vvvfSound.setPitch(1f + TrainSounds.swe_Signal$vvvfSawPitch(getCarriageSounds().getSpeedFactor().getValue(), 10));
        if (vvvfSound == null) {
            return;
        }
    }


    public void init(ICarriageSounds carriageSounds, CarriageContraptionEntity entity) {
        super.init(carriageSounds, entity);
        vvvfSound = new LoopingSound(
                AllSounds.TRAIN_VVVF.get(),
                SoundSource.NEUTRAL,
                entity.level().random
        );
        Minecraft.getInstance().getSoundManager().play(vvvfSound);
    }
}
