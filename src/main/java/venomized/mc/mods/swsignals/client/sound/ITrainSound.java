package venomized.mc.mods.swsignals.client.sound;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageSounds;
import venomized.mc.mods.swsignals.mixin.client.MixinCarriageSounds;

public interface ITrainSound {
    void tick(Carriage.DimensionalCarriageEntity entity);

    void submitSharedSoundVolume(CarriageContraptionEntity entity);

    void init(CarriageSounds mixinCarriageSounds);
}
