package venomized.mc.mods.swsignals;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SwSignal.MOD_ID);

    public static RegistryObject<SoundEvent> sound(String soundName) {
        return SOUNDS.register(soundName, ()->SoundEvent.createVariableRangeEvent(
            SwSignal.modLoc(soundName)
        ));
    }

    public static final RegistryObject<SoundEvent> SE_TEST = sound("se_crossing_bell_a");
    // public static final RegistryObject<SoundEvent> ATC_CONFIRM = sound("atc_confirm");

    public static final RegistryObject<SoundEvent> TRAIN_X31K_1 = sound("x31k1");
}
