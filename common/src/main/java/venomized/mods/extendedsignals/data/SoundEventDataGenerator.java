package venomized.mods.extendedsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

public class SoundEventDataGenerator extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public SoundEventDataGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, ExtendedSignalsCore.MOD_ID, helper);
    }

    private static final ResourceLocation modLoc(String p) {
        return ResourceLocation.fromNamespaceAndPath(ExtendedSignalsCore.MOD_ID, p);
    }

    /**
     *
     */
    @Override
    public void registerSounds() {

    }

    /**
     * Registers the sound definitions that should be generated via one of the {@code add} methods.
     */
    // @Override
    // public void registerSounds() {
    //     AllSounds.SOUNDS.getEntries().forEach(sound -> {
    //         this.add(
    //                 sound.get(), SoundDefinition.definition()
    //                         .with(SoundDefinition.Sound.sound(sound.getId(), SoundDefinition.SoundType.SOUND)
    //                         ));
    //     });
    // }
}
