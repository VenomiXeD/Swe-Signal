package venomized.mods.extendedsignals.core.menu;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.MenuEntry;
import net.neoforged.bus.api.IEventBus;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.client.screen.ScreenModelConfig;
import venomized.mods.extendedsignals.core.client.screen.ScreenModifierPoint;

public final class CoreMenus {
    public static final MenuEntry<MenuModelConfig> MODEL_CONFIG = registrate()
            .menu("model_config", MenuModelConfig::new, () -> ScreenModelConfig::new)
            .register();


    public static final MenuEntry<MenuModifierPoint> MODIFIER_POINT = registrate()
            .menu("point_config", MenuModifierPoint::new, () -> ScreenModifierPoint::new)
            .register();

    public static Registrate registrate() {
        return ExtendedSignals.REGISTRATE.get();
    }

    public static void register(IEventBus bus) {
    }
}
