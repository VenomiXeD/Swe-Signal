package venomized.mods.extendedsignals.core.client.sound;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.util.Mth;
import venomized.mods.extendedsignals.core.client.MiscTrainDataClientSync;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;
import venomized.mods.extendedsignals.core.util.MathHelp;

import java.util.Collection;
import java.util.List;

public class VVVFSoundMap {
    private final LoopingSound asyncSound;

    private final List<SoundCurve> soundCurveMappings = new ReferenceArrayList<>();

    public VVVFSoundMap() {
        this(null);
    }

    public VVVFSoundMap(LoopingSound asyncStartSound) {
        asyncSound = asyncStartSound;
    }

    public void evaluate(MiscTrainDataClientSync.TrainData data) {
        boolean phaseSoundFound = false;
        for (SoundCurve soundCurveMapping : soundCurveMappings) {
            double vKph = MathHelp.KphFromMs(Mth.abs((float) data.speed()));
            if (soundCurveMapping.mapFromSpeedKph <= vKph && soundCurveMapping.mapToSpeedKph >= vKph) {
                double pitchFactor = Mth.clampedMap(vKph, soundCurveMapping.mapFromSpeedKph, soundCurveMapping.mapToSpeedKph, soundCurveMapping.pitchFactor0, soundCurveMapping.pitchFactor1);
                soundCurveMapping.sounds.forEach(soundCurve -> {
                    soundCurve.setVolume(1);
                    soundCurve.setPitch((float) pitchFactor);
                });
                phaseSoundFound = true;
            } else {
                soundCurveMapping.sounds.forEach(soundCurve -> soundCurve.setVolume(0));
            }
        }

        if (asyncSound != null) {
            if (phaseSoundFound)
                asyncSound.setVolume(0);
            else
                asyncSound.setVolume(Mth.abs((float) data.speed()) > 0d ? 1f : 0f);
        }
    }

    public void addSoundCurve(SoundCurve curve) {
        soundCurveMappings.add(curve);
    }

    public record SoundCurve(float mapFromSpeedKph, float mapToSpeedKph, float pitchFactor0, float pitchFactor1,
                             Collection<LoopingSound> sounds) {
    }
}
