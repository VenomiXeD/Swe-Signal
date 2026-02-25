package venomized.mc.mods.swsignals;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import venomized.mc.mods.swsignals.core.SwSignal;

public class AllSounds {
    //public static final RegistryEntry<SoundEvent> SOUNDS = SwSignal.REGISTRATE.get().sim(ForgeRegistries.SOUND_EVENTS.getRegistryKey());
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, SwSignal.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> SE_CROSSING_BELL = sound("se_crossing_bell_a");
    public static final DeferredHolder<SoundEvent, SoundEvent> SE_ATC_TONE = sound("atc");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRAIN_X31K_1 = sound("x31k1");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRAIN_VVVF = sound("vvvf");
    // public static final RegistryObject<SoundEvent> ATC_CONFIRM = sound("atc_confirm");

    public static DeferredHolder<SoundEvent, SoundEvent> sound(String soundName) {
        return SOUNDS.register(soundName, () -> SoundEvent.createVariableRangeEvent(
                SwSignal.resource(soundName)
        ));
    }
}
