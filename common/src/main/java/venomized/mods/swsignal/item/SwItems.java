package venomized.mods.swsignal.item;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;
import venomized.mods.swsignal.core.SwSignalCore;

public class SwItems {
    public static final ItemEntry<ItemSignalTuner> SIGNAL_TUNER =
            SwSignalCore.REGISTRATE.get().item("signalitem", ItemSignalTuner::new)
                    .tab(SwSignalCore.SW_SIGNAL_TAB.getKey())
                    .register();

    // public static final ItemEntry<ItemTest> ITEM_TEST = item(
    //         "item_test",
    //         ItemTest::new
    // )
    //         .register();

    public static void init() {
    }

    public static <T extends Item> ItemBuilder<T, Registrate> item(String iName, NonNullFunction<Item.Properties, T> iFactory) {
        return SwSignalCore.REGISTRATE.get().item(iName, iFactory);
    }
//
    // public static final RegistryObject<Item> LIGHT_BULB = ITEMS.register("light_bulb",
    // 		() -> new Item(new Item.Properties()));
//
    // // TODO: For testing purposes
    // public static final RegistryObject<BlockItem> ITEM_TEST = ITEMS.register("test_test",
    // 		() -> new BlockItem(SwBlocks.BLOCK_TEST.get(), new Item.Properties()));
//
    // public static final RegistryObject<TrackTargetingBlockItem> ITEM_ATC_CONTROLLER = ITEMS.register("atc_controller", () -> new TrackTargetingBlockItem(SeBlocks.BLOCK_ATC_CONTROLLER.get(), new Item.Properties(), ATCController.ATC));
}