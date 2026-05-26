package venomized.mods.extendedsignals.se;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import venomized.mods.extendedsignals.core.ModTemplate;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

@Mod(ExtendedSignalsSweden.MOD_ID)
public class ExtendedSignalsSweden extends ModTemplate {
    public static final String MOD_ID = "extended_signals_se";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab> CREATIVE_TAB = REGISTRATE.get().defaultCreativeTab(MOD_ID).register();

    public ExtendedSignalsSweden(FMLJavaModLoadingContext context) {
        super(context);
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
        SwedenBlockEntities.init();

        ExtendedSignalsSwedenSounds.init();
    }

    /**
     *
     */
    @Override
    protected void clientInitialization() {

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


}
