package venomized.mods.extendedsignals.se.item;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

public class SwedenItems {
    public static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    public static final ItemEntry<Item> INCOMPLETE_SIGNAL =
            registrate().item("incomplete_signal", Item::new)
                    .model((a, b) -> b.itemTexture(() -> Items.IRON_INGOT))
                    .register();

    public static void init() {
    }
}
