package venomized.mods.extendedsignals.core.item;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

public class ExtendedSignalsItems {
    public static final ItemEntry<ItemSignalTuner> SIGNAL_TUNER =
            ExtendedSignalsCore.REGISTRATE.get().item("signalitem", ItemSignalTuner::new)
                    .tab(ExtendedSignalsCore.CREATIVE_TAB.getKey())
                    .register();

    // public static final ItemEntry<ItemTest> ITEM_TEST = item(
    //         "item_test",
    //         ItemTest::new
    // )
    //         .register();

    public static void init() {
    }

    public static <T extends Item> ItemBuilder<T, Registrate> item(String iName, NonNullFunction<Item.Properties, T> iFactory) {
        return ExtendedSignalsCore.REGISTRATE.get().item(iName, iFactory);
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