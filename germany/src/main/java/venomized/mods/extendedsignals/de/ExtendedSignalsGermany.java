package venomized.mods.extendedsignals.de;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import venomized.mods.extendedsignals.core.data.SoundEventDataGenerator;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

@Mod(ExtendedSignalsGermany.MOD_ID)
public class ExtendedSignalsGermany {
    public static final String MOD_ID = "extended_signals_de";

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab, ?> CREATIVE_TAB = REGISTRATE.get()
            .defaultCreativeTab(MOD_ID)
            .register();

    /**
     * @param context
     */
    public ExtendedSignalsGermany(IEventBus context) {
        GermanyBlocks.init();
        GermanyBlockEntities.init();

        GermanySounds.init(context);

        context.register(ExtendedSignalsGermany.class);
    }


    public static ResourceLocation res(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }

    @SubscribeEvent
    public static void onDataGenerator(GatherDataEvent event) {
        event.addProvider(new SoundEventDataGenerator(MOD_ID, event.getGenerator().getPackOutput(), event.getExistingFileHelper(), GermanySounds.SOUNDS));
    }
}
