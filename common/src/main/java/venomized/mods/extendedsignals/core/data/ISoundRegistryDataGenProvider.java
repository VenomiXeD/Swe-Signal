package venomized.mods.extendedsignals.core.data;

import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public interface ISoundRegistryDataGenProvider {
    DeferredRegister<SoundEvent> sounds();

    Map<DeferredHolder<SoundEvent, SoundEvent>, Integer> attentionValues();
}
