package venomized.mods.extendedsignals.pl;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import venomized.mods.extendedsignals.core.registry.ExtendedSignalsRegistrate;
import venomized.mods.extendedsignals.pl.block.signals.PolandBlocks;

@Mod(ExtendedSignalsPoland.MOD_ID)
public class ExtendedSignalsPoland {
    public static final String MOD_ID = "extended_signals_pl";

    public static NonNullSupplier<ExtendedSignalsRegistrate> REGISTRATE = NonNullSupplier.lazy(() -> ExtendedSignalsRegistrate.create(MOD_ID));

    public static RegistryEntry<CreativeModeTab, ?> CREATIVE_TAB = REGISTRATE.get()
            .defaultCreativeTab(MOD_ID)
            .register();

    /**
     * @param context
     */
    public ExtendedSignalsPoland(IEventBus context) {
        PolandBlocks.init();
        // NetherlandBlockEntities.init();
//
        // NetherlandSounds.init(context);

        context.register(ExtendedSignalsPoland.class);
    }


    public static ResourceLocation res(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }

    @SubscribeEvent
    public static void onDataGenerator(GatherDataEvent event) {
        // event.addProvider(new SoundEventDataGenerator(MOD_ID, event.getGenerator().getPackOutput(), event.getExistingFileHelper(), NetherlandSounds.SOUNDS));
    }
}
