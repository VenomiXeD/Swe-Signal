package venomized.mods.extendedsignals.se;

import com.simibubi.create.AllSoundEvents;
import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ExtendedSignalsSwedenSounds {
    // //public static final RegistryEntry<SoundEvent> SOUNDS = SwSignal.REGISTRATE.get().sim(ForgeRegistries.SOUND_EVENTS.getRegistryKey());
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExtendedSignalsSweden.MOD_ID);
    public static final RegistryObject<SoundEvent> SE_CROSSING_BELL = sound(ExtendedSignalsSweden.res("crossing_bell"));
    // public static final RegistryObject<SoundEvent> SE_ATC_TONE = sound("atc");
    // public static final RegistryObject<SoundEvent> TRAIN_X31K_1 = sound("x31k1");
    public static final RegistryObject<SoundEvent> TRAIN_VVVF = sound(ExtendedSignalsSweden.res("vvvf"));

    // // public static final RegistryObject<SoundEvent> ATC_CONFIRM = sound("atc_confirm");
//
    public static RegistryObject<SoundEvent> sound(ResourceLocation sound) {
        return SOUNDS.register(sound.getPath(), () -> SoundEvent.createVariableRangeEvent(sound));
    }

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
