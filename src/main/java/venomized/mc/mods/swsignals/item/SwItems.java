package venomized.mc.mods.swsignals.item;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import venomized.mc.mods.swsignals.SwSignal;

public class SwItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SwSignal.MOD_ID);
    // public static final RegistryObject<BlockItem> ITEM_SW_SIGNAL_BOX = ITEMS.register("sw_signal_box", () -> new BlockItem(SeBlocks.BLOCK_SIGNAL_BOX.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_RAILROAD_CROSSING_CONTROLLER = ITEMS.register("railroad_crossing_controller", () -> new BlockItem(SeBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER.get(), new Item.Properties()));
//
    // public static final RegistryObject<CustomModelBlockItem> ITEM_TWO_LIGHT_SIGNAL = ITEMS.register(
    // 		"se_2l_signal",
    // 		() -> new CustomModelBlockItem(SeBlocks.BLOCK_TWO_LIGHT_SIGNAL.get(), new Item.Properties(), "", 0, false));
    // public static final RegistryObject<BlockItem> ITEM_THREE_LIGHT_SIGNAL = ITEMS.register(
    // 		"se_3l_signal",
    // 		() -> new BlockItem(SeBlocks.BLOCK_THREE_LIGHT_SIGNAL.get(), new Item.Properties()));
    // public static final RegistryObject<BlockItem> ITEM_FOUR_LIGHT_SIGNAL = ITEMS.register(
    // 		"se_4l_signal",
    // 		() -> new BlockItem(SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL.get(), new Item.Properties()));
    // public static final RegistryObject<BlockItem> ITEM_FIVE_LIGHT_SIGNAL = ITEMS.register(
    // 		"se_5l_signal",
    // 		() -> new BlockItem(SeBlocks.BLOCK_FIVE_LIGHT_SIGNAL.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_THREE_LIGHT_DISTANT_SIGNAL = ITEMS.register("sw_3l_distant_signal", () -> new BlockItem(SeBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_DWARF_SIGNAL = ITEMS.register("sw_dwarf_signal_modern", () -> new BlockItem(SeBlocks.BLOCK_MODERN_DWARF_SIGNAL.get(), new Item.Properties()));
    // public static final RegistryObject<BlockItem> ITEM_MAIN_DWARF_SIGNAL = ITEMS.register("sw_main_dwarf_signal_modern", () -> new BlockItem(SeBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_ENDPOINT_SIGNAL = ITEMS.register(
    // 		"se_1l_endpoint_signal",
    // 		() -> new BlockItem(SeBlocks.BLOCK_ENDPOINT_SIGNAL.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_RAILROAD_CROSSING_SIGNAL = ITEMS.register("sw_railroad_crossing_signal", () -> new BlockItem(SeBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL.get(), new Item.Properties()));
    // public static final RegistryObject<BlockItem> ITEM_RAILROAD_CROSSING_DISTANT_SIGNAL = ITEMS.register("sw_railroad_crossing_distant_signal", () -> new BlockItem(SeBlocks.BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_CROSSING_GATE = ITEMS.register("se_crossing_gate", () -> new BlockItem(SeBlocks.BLOCK_CROSSING_GATE.get(), new Item.Properties()));
//
    // public static final RegistryObject<BlockItem> ITEM_U_SIGN = ITEMS.register("sw_u_sign", () -> new BlockItem(SeBlocks.BLOCK_U_SIGN.get(), new Item.Properties()));
//
    public static final ItemEntry<ItemSignalTuner> ITEM_SIGNAL_TEST =
            SwSignal.REGISTRATE.get().item("signalitem",
                            ItemSignalTuner::new)
                    .register();

    public static void init() {
    }

    public static <T extends Item> ItemBuilder<T, Registrate> item(String iName, NonNullFunction<Item.Properties, T> iFactory) {
        return SwSignal.REGISTRATE.get().item(iName, iFactory);
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