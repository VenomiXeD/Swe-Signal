package venomized.mods.extendedsignals.de;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import venomized.mods.extendedsignals.core.ModTemplate;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;
import venomized.mods.extendedsignals.de.blockentity.GermanyBlockEntities;

@Mod(ExtendedSignalsGermany.MOD_ID)
public class ExtendedSignalsGermany extends ModTemplate {
    public static final String MOD_ID = "extended_signals_de";

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public static final RegistryEntry<CreativeModeTab> CREATIVE_TAB = REGISTRATE.get()
            .defaultCreativeTab(MOD_ID)
            .register();

    /**
     * @param context
     */
    public ExtendedSignalsGermany(FMLJavaModLoadingContext context) {
        super(context);
    }

    /**
     *
     */
    @Override
    protected void commonInitialization() {
        GermanyBlocks.init();
        GermanyBlockEntities.init();
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
