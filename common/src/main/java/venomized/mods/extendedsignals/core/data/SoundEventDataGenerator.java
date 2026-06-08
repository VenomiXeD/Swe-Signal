package venomized.mods.extendedsignals.core.data;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.DeferredRegister;

public class SoundEventDataGenerator extends SoundDefinitionsProvider {
    private final DeferredRegister<SoundEvent> soundRegistry;

    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public SoundEventDataGenerator(String modId, PackOutput output, ExistingFileHelper helper, DeferredRegister<SoundEvent> soundRegistry) {
        super(output, modId, helper);
        this.soundRegistry = soundRegistry;
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
                    )
            );
        });
    }
}
