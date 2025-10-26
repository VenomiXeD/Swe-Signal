package venomized.mc.mods.swsignals.client.sound.train;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.world.phys.Vec3;

public abstract class TrainSound {
    @Getter(AccessLevel.PROTECTED)
    private ICarriageSounds carriageSounds;

    public void tick(Carriage.DimensionalCarriageEntity entity) {
    }

    public void submitSharedSoundVolume(Vec3 location, float volume, CarriageContraptionEntity entity) {
    }

    public void init(ICarriageSounds carriageSounds, CarriageContraptionEntity entity) {
        this.carriageSounds = carriageSounds;
    }
}
