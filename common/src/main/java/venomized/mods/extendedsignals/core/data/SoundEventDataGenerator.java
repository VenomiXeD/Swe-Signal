package venomized.mods.extendedsignals.core.data;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public class SoundEventDataGenerator extends SoundDefinitionsProvider {
    private final DeferredRegister<SoundEvent> soundRegistry;
    private final Map<DeferredHolder<SoundEvent, SoundEvent>, Integer> attenuationDistanceValues;

    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public SoundEventDataGenerator(String modId, PackOutput output, ExistingFileHelper helper, DeferredRegister<SoundEvent> soundRegistry) {
        this(modId, output, helper, soundRegistry, Map.of());
    }

    public SoundEventDataGenerator(String modId, PackOutput output, ExistingFileHelper helper, DeferredRegister<SoundEvent> soundRegistry, Map<DeferredHolder<SoundEvent, SoundEvent>, Integer> attenuationDistances) {
        super(output, modId, helper);
        this.soundRegistry = soundRegistry;
        this.attenuationDistanceValues = attenuationDistances;
    }

    /**
     *
     */
    @Override
    public void registerSounds() {
        soundRegistry.getEntries().forEach(sound -> {
            this.add(sound.getId(),
                    SoundDefinition.definition().with(
                            SoundDefinition.Sound.sound(
                                    sound.getId(), SoundDefinition.SoundType.SOUND
                            )
                                    .attenuationDistance(attenuationDistanceValues.getOrDefault(sound, 16))
                    )
            );
        });
    }
}
