package venomized.mc.mods.swsignals.client.sound.train;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import venomized.mc.mods.swsignals.client.sound.LoopingSound;

import java.util.List;

public abstract class TrainSound {
    @Getter(AccessLevel.PROTECTED)
    private ICarriageSounds internalCarriageSounds;

    private final List<LoopingSound> playedSounds = new ObjectArrayList<>();

    public void init(ICarriageSounds carriageSounds, CarriageContraptionEntity entity) {
        this.internalCarriageSounds = carriageSounds;
    }

    public void destroySounds() {
        playedSounds.forEach(LoopingSound::cleanup);
        playedSounds.clear();
    }

    public void tick(Carriage.DimensionalCarriageEntity entity) {
    }

    public void submitSharedSoundVolume(Vec3 location, float volume, CarriageContraptionEntity entity) {
    }

    public void closingDoors(CarriageContraptionEntity presentEntity) {
    }

    protected LoopingSound playSound(SoundEvent sound) {
        LoopingSound loopingSound = new LoopingSound(
                sound,
                SoundSource.NEUTRAL,
                SoundInstance.createUnseededRandom()
        );

        this.playedSounds.add(loopingSound);

        Minecraft.getInstance().getSoundManager().play(loopingSound);

        return loopingSound;
    }
}
