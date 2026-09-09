package venomized.mods.extendedsignals.core.client.sound.train;

import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import venomized.mods.extendedsignals.core.client.MiscTrainDataClientSync;
import venomized.mods.extendedsignals.core.client.sound.LoopingSound;

import java.util.List;

public abstract class TrainSound {
    private final List<LoopingSound> playedSounds = new ReferenceArrayList<>();
    private final List<ChorusSound> chorusSounds = new ReferenceArrayList<>();
    @Getter(AccessLevel.PROTECTED)
    private ICarriageSounds internalCarriageSounds;
    @Getter(AccessLevel.PROTECTED)
    private CarriageContraptionEntity carriageEntity;

    public void init(ICarriageSounds carriageSounds, CarriageContraptionEntity entity) {
        internalCarriageSounds = carriageSounds;
        carriageEntity = entity;
    }

    public void destroySounds() {
        chorusSounds.forEach(s -> s.chorusSound.cleanup());
        chorusSounds.clear();
        playedSounds.forEach(LoopingSound::cleanup);
        playedSounds.clear();
    }

    public void tick(Carriage.DimensionalCarriageEntity entity) {
    }

    public void submitSharedSoundVolume(Vec3 location, CarriageContraptionEntity entity, MiscTrainDataClientSync.TrainData trainData) {
        playedSounds.forEach(e -> e.setLocation(location));
        chorusSounds.forEach(e -> {
            e.chorusSound.setLocation(location);
            e.chorusSound.setPitch(e.from.getPitch() * e.pitchMultiplier - e.pitchOffset);
            e.chorusSound.setVolume(e.from.getVolume());
        });
    }

    public void closingDoors(CarriageContraptionEntity presentEntity) {
    }

    protected LoopingSound playSound(SoundEvent sound, boolean chorus, float pitchMultiplier, float pitchOffset) {
        LoopingSound loopingSound = new LoopingSound(
                sound,
                SoundSource.BLOCKS,
                SoundInstance.createUnseededRandom()
        );
        if (chorus) {
            LoopingSound chorusSound = new LoopingSound(
                    sound,
                    SoundSource.NEUTRAL,
                    SoundInstance.createUnseededRandom()
            );
            chorusSounds.add(new ChorusSound(loopingSound, chorusSound, pitchMultiplier, pitchOffset));
            Minecraft.getInstance().getSoundManager().play(chorusSound);
        }
        this.playedSounds.add(loopingSound);

        Minecraft.getInstance().getSoundManager().play(loopingSound);

        return loopingSound;
    }

    protected LoopingSound playSound(SoundEvent sound, boolean chorus) {
        return playSound(sound, chorus, 1.0f, -0.01f);
    }

    protected LoopingSound playSound(SoundEvent sound) {
        return playSound(sound, false);
    }

    private record ChorusSound(SoundInstance from, LoopingSound chorusSound, float pitchMultiplier, float pitchOffset) {
    }
}
