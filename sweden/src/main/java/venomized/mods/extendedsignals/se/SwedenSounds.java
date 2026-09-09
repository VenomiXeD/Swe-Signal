package venomized.mods.extendedsignals.se;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SwedenSounds {
    // //public static final RegistryEntry<SoundEvent> SOUNDS = SwSignal.REGISTRATE.get().sim(ForgeRegistries.SOUND_EVENTS.getRegistryKey());
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, ExtendedSignalsSweden.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> SE_CROSSING_BELL = sound(ExtendedSignalsSweden.res("crossing_bell"));
    // public static final RegistryObject<SoundEvent> SE_ATC_TONE = sound("atc");
    // public static final RegistryObject<SoundEvent> TRAIN_X31K_1 = sound("x31k1");
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_HUM = sound(ExtendedSignalsSweden.res("onix1500_hum"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_ASYNC = sound(ExtendedSignalsSweden.res("onix1500_async"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P0_ACC = sound(ExtendedSignalsSweden.res("onix1500_p0_acc"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P1_ACC = sound(ExtendedSignalsSweden.res("onix1500_p1_acc"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P2_ACC = sound(ExtendedSignalsSweden.res("onix1500_p2_acc"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P3_ACC = sound(ExtendedSignalsSweden.res("onix1500_p3_acc"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P4_ACC = sound(ExtendedSignalsSweden.res("onix1500_p4_acc"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P5_ACC = sound(ExtendedSignalsSweden.res("onix1500_p5_acc"));
    public static final DeferredHolder<SoundEvent, SoundEvent> ONIX1500_P6_ACC = sound(ExtendedSignalsSweden.res("onix1500_p6_acc"));


    // // public static final RegistryObject<SoundEvent> ATC_CONFIRM = sound("atc_confirm");
//
    public static DeferredHolder<SoundEvent, SoundEvent> sound(ResourceLocation sound) {
        return SOUNDS.register(sound.getPath(), () -> SoundEvent.createVariableRangeEvent(sound));
    }

    public static void init(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
