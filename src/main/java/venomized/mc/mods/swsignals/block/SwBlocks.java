package venomized.mc.mods.swsignals.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.sw.*;

public final class SwBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

	public static final RegistryObject<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = BLOCKS.register("railroad_crossing_controller", BlockRailroadCrossingController::new);

	// == SWEDISH CONTENT ==
	public static final RegistryObject<BlockSignalBox> BLOCK_SW_SIGNAL_BOX = BLOCKS.register(BlockSignalBox.BLOCK_NAME, BlockSignalBox::new);

	// == SIGNALS FROM 2-5 LIGHTS ==
	public static final RegistryObject<BlockModernTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = BLOCKS.register(BlockModernTwoLightSignal.BLOCK_NAME, BlockModernTwoLightSignal::new);
	public static final RegistryObject<BlockModernThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = BLOCKS.register(BlockModernThreeLightSignal.BLOCK_NAME, BlockModernThreeLightSignal::new);
	public static final RegistryObject<BlockModernFourLightSignal> BLOCK_FOUR_LIGHT_SIGNAL = BLOCKS.register(BlockModernFourLightSignal.BLOCK_NAME, BlockModernFourLightSignal::new);
	public static final RegistryObject<BlockModernFiveLightSignal> BLOCK_FIVE_LIGHT_SIGNAL = BLOCKS.register(BlockModernFiveLightSignal.BLOCK_NAME, BlockModernFiveLightSignal::new);

	// == DISTANT SIGNALS ==
	public static final RegistryObject<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = BLOCKS.register("sw_3l_distant_signal", BlockModernThreeLightDistantSignal::new);

	// == DWARF SIGNALS ==
	public static final RegistryObject<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = BLOCKS.register("sw_dwarf_signal_modern", BlockModernDwarfSignal::new);

	// == MISC SIGNALS ==
	public static final RegistryObject<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = BLOCKS.register(BlockModernEndpointSignal.BLOCK_NAME, BlockModernEndpointSignal::new);

	public static final RegistryObject<BlockGeneric45DegreeBlock> BLOCK_U_SIGN = BLOCKS.register("sw_u_sign", BlockGeneric45DegreeBlock::new);

	// == RAILROAD CROSSING SIGNALS ==
	public static final RegistryObject<BlockRailroadCrossingObjectBase> BLOCK_RAILROAD_SIGNAL = BLOCKS.register("sw_railroad_crossing_signal", BlockRailroadCrossingSignal::new);
}
