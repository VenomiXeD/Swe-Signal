package venomized.mc.mods.swsignals.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.*;

public class SwItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SwSignal.MOD_ID);

	public static final RegistryObject<BlockItem> ITEM_TWO_LIGHT_SIGNAL = ITEMS.register(BlockModernTwoLightSignal.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_THREE_LIGHT_SIGNAL = ITEMS.register(BlockModernThreeLightSignal.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_THREE_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_FOUR_LIGHT_SIGNAL = ITEMS.register(BlockModernFourLightSignal.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_FOUR_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_FIVE_LIGHT_SIGNAL = ITEMS.register(BlockModernFiveLightSignal.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_FIVE_LIGHT_SIGNAL.get(), new Item.Properties()));

	public static final RegistryObject<BlockItem> ITEM_U_SIGN = ITEMS.register(BlockUSign.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_U_SIGN.get(), new Item.Properties()));

	public static final RegistryObject<CreateSignalTestItem> ITEM_SIGNAL_TEST = ITEMS.register("signalitem", CreateSignalTestItem::new);
}
