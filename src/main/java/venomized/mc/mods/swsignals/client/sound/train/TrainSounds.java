package venomized.mc.mods.swsignals.client.sound.train;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import venomized.mc.mods.swsignals.core.SwSignal;

public class TrainSounds {
    public static final ResourceKey<Registry<TrainSoundConfiguration<?>>> TRAIN_SOUNDS_RESOURCE_KEY = ResourceKey.createRegistryKey(SwSignal.resource("train_sounds"));
    public static final DeferredRegister<TrainSoundConfiguration<?>> TRAIN_SOUNDS_REGISTRY = DeferredRegister.create(TRAIN_SOUNDS_RESOURCE_KEY, SwSignal.MOD_ID);
    public static final DeferredHolder<TrainSoundConfiguration<?>, TrainSoundConfiguration<?>> TRAIN_X60 = TRAIN_SOUNDS_REGISTRY.register("x60", TrainSoundConfiguration.ofTrainSound(TrainSoundX60::new));

    public static float swe_Signal$vvvfSawPitch(float x, int cycles) {
        if (x < 0) x = 0;
        if (x > 1) x = 1;

        double decayPower = 2.0; // higher = faster decay of oscillations

        // fractional part of (x * cycles) — generates sawtooth wave
        double saw = (x * cycles) - Math.floor(x * cycles);

        // decaying amplitude factor
        double envelope = Math.pow(1 - x, decayPower);

        // combine ramp + decay + base rise
        double result = (saw * envelope) + (x * (1 - envelope));

        // clamp to [0, 1]
        return (float) Math.max(0, Math.min(1, result));
    }
}
