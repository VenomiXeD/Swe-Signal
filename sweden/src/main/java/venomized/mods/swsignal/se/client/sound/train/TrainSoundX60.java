package venomized.mods.extendedsignals.se.client.sound.train;

import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.client.sound.LoopingSound;
import venomized.mc.mods.swsignals.client.sound.train.ICarriageSounds;
import venomized.mc.mods.swsignals.client.sound.train.TrainSound;
import venomized.mc.mods.swsignals.client.sound.train.TrainSounds;

public class TrainSoundX60 extends TrainSound {
    private LoopingSound vvvfSound;

    /**
     * @param location
     * @param volume
     * @param entity
     */
    @Override
    public void submitSharedSoundVolume(Vec3 location, float volume, CarriageContraptionEntity entity) {
        vvvfSound.setLocation(location);
        float speed = getInternalCarriageSounds().getSpeedFactor().getValue();
        vvvfSound.setVolume(Mth.sqrt(speed));

        vvvfSound.setPitch(1f + TrainSounds.swe_Signal$vvvfSawPitch(speed, 12));
    }


    public void init(ICarriageSounds carriageSounds, CarriageContraptionEntity entity) {
        super.init(carriageSounds, entity);
        vvvfSound = playSound(AllSounds.TRAIN_VVVF.get());
    }
}
