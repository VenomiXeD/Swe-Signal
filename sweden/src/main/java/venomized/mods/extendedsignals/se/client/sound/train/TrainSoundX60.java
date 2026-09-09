package venomized.mods.extendedsignals.se.client.sound.train;

import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.phys.Vec3;
import venomized.mods.extendedsignals.core.client.MiscTrainDataClientSync;
import venomized.mods.extendedsignals.core.client.sound.VVVFSoundMap;
import venomized.mods.extendedsignals.core.client.sound.train.ICarriageSounds;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;
import venomized.mods.extendedsignals.se.SwedenSounds;

import java.util.Set;

public class TrainSoundX60 extends TrainSound {
    private VVVFSoundMap vvvfSoundMap;

    /**
     * @param location
     * @param entity
     * @param trainData
     */
    @Override
    public void submitSharedSoundVolume(Vec3 location, CarriageContraptionEntity entity, MiscTrainDataClientSync.TrainData trainData) {
        super.submitSharedSoundVolume(location, entity, trainData);
        vvvfSoundMap.evaluate(trainData);
    }


    public void init(ICarriageSounds carriageSounds, CarriageContraptionEntity entity) {
        super.init(carriageSounds, entity);
        vvvfSoundMap = new VVVFSoundMap(playSound(SwedenSounds.ONIX1500_ASYNC.get(), true));

        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        13f, 29f, 1f, 29f / 13f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P0_ACC.get(), false))
                )
        );

        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        29f, 40f, 1f, 40 / 29f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P1_ACC.get(), false))
                )
        );

        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        40f, 42.5f, 1f, 42.5f / 40f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P2_ACC.get(), false))
                )
        );
        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        42.5f, 44.3f, 1f, 44.3f / 42.5f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P3_ACC.get(), false))
                )
        );
        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        44.3f, 47f, 1f, 47f / 44.3f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P4_ACC.get(), false))
                )
        );
        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        47f, 49f, 1f, 49f / 47f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P5_ACC.get(), false))
                )
        );

        vvvfSoundMap.addSoundCurve(
                new VVVFSoundMap.SoundCurve(
                        49f, 160f, 1f, 160f / 49f, Set.of(
                        playSound(SwedenSounds.ONIX1500_P6_ACC.get(), false))
                )
        );
    }
}
