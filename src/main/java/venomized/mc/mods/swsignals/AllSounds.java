package venomized.mc.mods.swsignals;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllSounds {
    //public static final RegistryEntry<SoundEvent> SOUNDS = SwSignal.REGISTRATE.get().sim(ForgeRegistries.SOUND_EVENTS.getRegistryKey());
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SwSignal.MOD_ID);
    public static final RegistryObject<SoundEvent> SE_CROSSING_BELL = sound("se_crossing_bell_a");
    public static final RegistryObject<SoundEvent> SE_ATC_TONE = sound("atc");
    public static final RegistryObject<SoundEvent> TRAIN_X31K_1 = sound("x31k1");
    // public static final RegistryObject<SoundEvent> ATC_CONFIRM = sound("atc_confirm");

    public static RegistryObject<SoundEvent> sound(String soundName) {
        return SOUNDS.register(soundName, () -> SoundEvent.createVariableRangeEvent(
                SwSignal.modLoc(soundName)
        ));
    }
}
