package venomized.mc.mods.swsignals.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.BlockThreeLightSignal;
import venomized.mc.mods.swsignals.block.BlockTwoLightSignal;
import venomized.mc.mods.swsignals.block.SwBlocks;

public class SwItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SwSignal.MOD_ID);

	public static final RegistryObject<BlockItem> ITEM_TWO_LIGHT_SIGNAL = ITEMS.register(BlockTwoLightSignal.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<BlockItem> ITEM_THREE_LIGHT_SIGNAL = ITEMS.register(BlockThreeLightSignal.BLOCK_NAME, () -> new BlockItem(SwBlocks.BLOCK_THREE_LIGHT_SIGNAL.get(), new Item.Properties()));
	public static final RegistryObject<CreateSignalTestItem> ITEM_SIGNAL_TEST = ITEMS.register("signalitem", CreateSignalTestItem::new);
}
