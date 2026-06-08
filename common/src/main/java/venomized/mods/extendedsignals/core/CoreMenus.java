package venomized.mods.extendedsignals.core;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.MenuEntry;
import net.neoforged.bus.api.IEventBus;
import venomized.mods.extendedsignals.core.client.screen.ScreenModelConfig;
import venomized.mods.extendedsignals.core.menu.MenuModelConfig;

public final class CoreMenus {
    public static Registrate registrate() {
        return ExtendedSignals.REGISTRATE.get();
    }

    public static final MenuEntry<MenuModelConfig> MODEL_CONFIG = registrate()
            .menu("model_config", MenuModelConfig::new, () -> ScreenModelConfig::new)
            .register();

    public static void register(IEventBus bus) {
    }
}
