package venomized.mods.extendedsignals.se;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import venomized.mods.extendedsignals.core.Mod;
import venomized.mods.extendedsignals.core.data.SoundEventDataGenerator;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;
import venomized.mods.extendedsignals.se.client.SwedenModels;
import venomized.mods.extendedsignals.se.data.SwedenRecipes;
import venomized.mods.extendedsignals.se.item.SwedenItems;

@net.minecraftforge.fml.common.Mod(ExtendedSignalsSweden.MOD_ID)
public class ExtendedSignalsSweden extends Mod {
    public static final String MOD_ID = "extended_signals_se";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab> CREATIVE_TAB = REGISTRATE.get().defaultCreativeTab(MOD_ID).register();

    public ExtendedSignalsSweden(FMLJavaModLoadingContext context) {
        super(context);
        IEventBus bus = context.getModEventBus();
        bus.register(this);

        ExtendedSignalsSwedenSounds.init(bus);
    }

    public static ResourceLocation res(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }


    /**
     *
     */
    @Override
    protected void commonInitialization() {
        SwedenBlocks.init();
        SwedenItems.init();
        SwedenBlockEntities.init();


    }

    /**
     *
     */
    @Override
    protected void clientInitialization() {
        SwedenModels.init();
    }

    /**
     * @return
     */
    @Override
    protected RegistryEntry<CreativeModeTab> TAB_ENTRY() {
        return CREATIVE_TAB;
    }

    /**
     * @return
     */
    @Override
    protected Registrate REGISTRATE() {
        return REGISTRATE.get();
    }

    @SubscribeEvent
    public void onDataGeneration(GatherDataEvent e) {
        e.getGenerator().addProvider(true, new SoundEventDataGenerator(
                ExtendedSignalsSweden.MOD_ID,
                e.getGenerator().getPackOutput(),
                e.getExistingFileHelper(),
                ExtendedSignalsSwedenSounds.SOUNDS
        ));

        e.getGenerator().addProvider(e.includeServer(), new SwedenRecipes(e.getGenerator().getPackOutput(), e.getLookupProvider()));
    }
}
