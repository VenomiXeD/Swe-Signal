package venomized.mc.mods.swsignals.client;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.client.ui.MenuTest;


public final class SwMenus {
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SwSignal.MOD_ID);

	public static final RegistryObject<MenuType<MenuTest>> MENU_TEST = MENUS.register("test", () -> IForgeMenuType.create(MenuTest::new));
}
