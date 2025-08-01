package venomized.mc.mods.swsignals;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SwSignal.MOD_ID);

	public static final RegistryObject<SoundEvent> SE_TEST = SOUNDS.register("sw_crossing_bell", () ->
			SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, "se_crossing_bell_a"), 32f)
	);

	public static final RegistryObject<SoundEvent> ATC_CONFIRM = SOUNDS.register("atc_confirm", () ->
			SoundEvent.createFixedRangeEvent(SwSignal.modLoc("atc_confirm"), 32f)
	);
}
