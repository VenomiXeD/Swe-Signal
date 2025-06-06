package venomized.mc.mods.swsignals.item;

import com.simibubi.create.content.trains.track.TrackTargetingBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.SwBlocks;
import venomized.mc.mods.swsignals.block.sw.*;
import venomized.mc.mods.swsignals.client.CustomModelBlockItem;
import venomized.mc.mods.swsignals.create.tracks.ATCController;

public class SwItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SwSignal.MOD_ID);

	public static final RegistryObject<BlockItem> ITEM_SW_SIGNAL_BOX = ITEMS.register("sw_signal_box", () -> new BlockItem(SwBlocks.BLOCK_SW_SIGNAL_BOX.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_RAILROAD_CROSSING_CONTROLLER = ITEMS.register("railroad_crossing_controller", () -> new BlockItem(SwBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER.get(), new Item.Properties()));

	public static final RegistryObject<CustomModelBlockItem> ITEM_TWO_LIGHT_SIGNAL = ITEMS.register(
			BlockModernTwoLightSignal.BLOCK_NAME,
			() -> new CustomModelBlockItem(SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get(), new Item.Properties(), "", 0, false));
	public static final RegistryObject<BlockItem> ITEM_THREE_LIGHT_SIGNAL = ITEMS.register(
			BlockModernThreeLightSignal.BLOCK_NAME,
			() -> new BlockItem(SwBlocks.BLOCK_THREE_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_FOUR_LIGHT_SIGNAL = ITEMS.register(
			BlockModernFourLightSignal.BLOCK_NAME,
			() -> new BlockItem(SwBlocks.BLOCK_FOUR_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_FIVE_LIGHT_SIGNAL = ITEMS.register(
			BlockModernFiveLightSignal.BLOCK_NAME,
			() -> new BlockItem(SwBlocks.BLOCK_FIVE_LIGHT_SIGNAL.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_THREE_LIGHT_DISTANT_SIGNAL = ITEMS.register("sw_3l_distant_signal", () -> new BlockItem(SwBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_DWARF_SIGNAL = ITEMS.register("sw_dwarf_signal_modern", () -> new BlockItem(SwBlocks.BLOCK_MODERN_DWARF_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_MAIN_DWARF_SIGNAL = ITEMS.register("sw_main_dwarf_signal_modern", () -> new BlockItem(SwBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_ENDPOINT_SIGNAL = ITEMS.register(
			BlockModernEndpointSignal.BLOCK_NAME,
			() -> new BlockItem(SwBlocks.BLOCK_ENDPOINT_SIGNAL.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_RAILROAD_CROSSING_SIGNAL = ITEMS.register("sw_railroad_crossing_signal", () -> new BlockItem(SwBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_RAILROAD_CROSSING_DISTANT_SIGNAL = ITEMS.register("sw_railroad_crossing_distant_signal", () -> new BlockItem(SwBlocks.BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_U_SIGN = ITEMS.register("sw_u_sign", () -> new BlockItem(SwBlocks.BLOCK_U_SIGN.get(), new Item.Properties()));

	public static final RegistryObject<ItemSignalTuner> ITEM_SIGNAL_TEST = ITEMS.register("signalitem",
			ItemSignalTuner::new);

	// TODO: For testing purposes
	public static final RegistryObject<BlockItem> ITEM_TEST = ITEMS.register("test_test",
			() -> new BlockItem(SwBlocks.BLOCK_TEST.get(), new Item.Properties()));

	public static final RegistryObject<TrackTargetingBlockItem> ITEM_ATC_CONTROLLER = ITEMS.register("atc_controller", () -> new TrackTargetingBlockItem(SwBlocks.BLOCK_ATC_CONTROLLER.get(), new Item.Properties(), ATCController.ATC));
}
