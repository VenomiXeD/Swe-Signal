package venomized.mods.extendedsignals.core;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mods.extendedsignals.core.client.screen.ScreenModelConfig;
import venomized.mods.extendedsignals.core.menu.MenuModelConfig;

public final class CoreMenus {
    public static Registrate registrate() {
        return ExtendedSignalsCore.REGISTRATE.get();
    }

    public static final MenuEntry<MenuModelConfig> MODEL_CONFIG = registrate()
            .menu("model_config", MenuModelConfig::new, () -> ScreenModelConfig::new)
            .register();

    public static void register(IEventBus bus) {
    }
}
