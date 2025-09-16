package venomized.mc.mods.swsignals.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.SwSignal;

public class SoundEventDataGenerator extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public SoundEventDataGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, SwSignal.MOD_ID, helper);
    }

    private static final ResourceLocation modLoc(String p) {
        return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, p);
    }

    /**
     * Registers the sound definitions that should be generated via one of the {@code add} methods.
     */
    @Override
    public void registerSounds() {
        this.add(AllSounds.SE_TEST.get(), SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(modLoc("se_crossing_bell_a"), SoundDefinition.SoundType.SOUND)
                )
        );

        // this.add(AllSounds.ATC_CONFIRM.get(), SoundDefinition.definition().with(
        // 		SoundDefinition.Sound.sound(SoundEvents.NOTE_BLOCK_PLING.get().getLocation(), SoundDefinition.SoundType.SOUND)
        // ));
    }
}
