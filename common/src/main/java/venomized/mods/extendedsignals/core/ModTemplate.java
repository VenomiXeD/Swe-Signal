package venomized.mods.extendedsignals.core;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class ModTemplate {
    public ModTemplate(FMLJavaModLoadingContext context) {
        commonInitialization();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::clientInitialization);
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> this::serverInitialization);

        context.getModEventBus().addListener(this::onCreativeTabBuildContents);
    }

    protected void commonInitialization() {
    }

    protected void clientInitialization() {
    }

    protected void serverInitialization() {
    }

    protected abstract RegistryEntry<CreativeModeTab> TAB_ENTRY();

    protected abstract Registrate REGISTRATE();

    public void onCreativeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == TAB_ENTRY().getKey()) {
            REGISTRATE().getAll(Registries.ITEM).forEach(item -> event.accept(item.get()));
        }
    }
}
