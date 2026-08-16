package venomized.mods.extendedsignals.se.client.sound.train;

import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import venomized.mods.extendedsignals.core.client.sound.LoopingSound;
import venomized.mods.extendedsignals.core.client.sound.train.ICarriageSounds;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSounds;
import venomized.mods.extendedsignals.se.SwedenSounds;

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
        vvvfSound = playSound(SwedenSounds.TRAIN_VVVF.get());
    }
}
