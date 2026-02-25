package venomized.mc.mods.swsignals.client;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import venomized.mc.mods.swsignals.client.ui.MenuTest;
import venomized.mc.mods.swsignals.core.SwSignal;

import java.util.function.Supplier;


public final class SwMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, SwSignal.MOD_ID);

    public static final Supplier<MenuType<MenuTest>> MENU_TEST = MENUS.register("test", () -> IMenuTypeExtension.create(MenuTest::new));
}
