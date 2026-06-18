package venomized.mods.extendedsignals.se;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import venomized.mods.extendedsignals.core.data.SoundEventDataGenerator;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;
import venomized.mods.extendedsignals.se.client.SwedenModels;
import venomized.mods.extendedsignals.se.data.SwedenRecipes;

@Mod(ExtendedSignalsSweden.MOD_ID)
public class ExtendedSignalsSweden {
    public static final String MOD_ID = "extended_signals_se";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab, ?> CREATIVE_TAB = REGISTRATE.get().defaultCreativeTab(MOD_ID).register();

    public ExtendedSignalsSweden(ModContainer mod) {
        IEventBus bus = mod.getEventBus();
        bus.register(ExtendedSignalsSweden.class);

        SwedenBlocks.init();
        SwedenBlockEntities.init();

        ExtendedSignalsSwedenSounds.init(bus);
    }

    public static ResourceLocation res(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void onDataGeneration(GatherDataEvent e) {
        e.getGenerator().addProvider(true, new SoundEventDataGenerator(
                ExtendedSignalsSweden.MOD_ID,
                e.getGenerator().getPackOutput(),
                e.getExistingFileHelper(),
                ExtendedSignalsSwedenSounds.SOUNDS
        ));

        e.getGenerator().addProvider(
                e.includeServer(),
                new SwedenRecipes(e.getGenerator().getPackOutput(), e.getLookupProvider())
        );
    }
}
