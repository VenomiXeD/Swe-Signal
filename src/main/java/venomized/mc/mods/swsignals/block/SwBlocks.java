package venomized.mc.mods.swsignals.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.sw.*;
import venomized.mc.mods.swsignals.block.test.TestBlock;

public final class SwBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SwSignal.MOD_ID);

	public static final RegistryObject<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = BLOCKS.register("railroad_crossing_controller", BlockRailroadCrossingController::new);

	// == SWEDISH CONTENT ==
	public static final RegistryObject<BlockSignalBox> BLOCK_SW_SIGNAL_BOX = BLOCKS.register("signals.se.signal_box", BlockSignalBox::new);

	// == SIGNALS FROM 2-5 LIGHTS ==
	public static final RegistryObject<BlockModernTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = BLOCKS.register("signals.se.2l_signal_post_1970", BlockModernTwoLightSignal::new);
	public static final RegistryObject<BlockModernThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = BLOCKS.register("signals.se.3l_signal_post_1970", BlockModernThreeLightSignal::new);
	public static final RegistryObject<BlockModernFourLightSignal> BLOCK_FOUR_LIGHT_SIGNAL = BLOCKS.register("signals.se.4l_signal_post_1970", BlockModernFourLightSignal::new);
	public static final RegistryObject<BlockModernFiveLightSignal> BLOCK_FIVE_LIGHT_SIGNAL = BLOCKS.register("signals.se.5l_signal_post_1970", BlockModernFiveLightSignal::new);

	// == DISTANT SIGNALS ==
	public static final RegistryObject<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = BLOCKS.register("signals.se.3l_distant_signal_post_1970", BlockModernThreeLightDistantSignal::new);

	// == DWARF SIGNALS ==
	public static final RegistryObject<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = BLOCKS.register("signals.se.4l_dwarf_signal_post_1970", BlockModernDwarfSignal::new);

	// == MISC SIGNALS ==
	public static final RegistryObject<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = BLOCKS.register("signals.se.1l_signal_endpoint_post_1920", BlockModernEndpointSignal::new);

	public static final RegistryObject<BlockGeneric45DegreeBlock> BLOCK_U_SIGN = BLOCKS.register("signals.se.u_sign", BlockGeneric45DegreeBlock::new);

	// == RAILROAD CROSSING SIGNALS ==
	public static final RegistryObject<BlockRailroadCrossingSignal> BLOCK_RAILROAD_CROSSING_SIGNAL = BLOCKS.register("signals.se.1l_railroad_crossing_signal_2", BlockRailroadCrossingSignal::new);

	public static final RegistryObject<BlockRailroadCrossingDistantSignal> BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL = BLOCKS.register(
			"signals.se.3l_distant_railroad_crossing_signal", BlockRailroadCrossingDistantSignal::new
	);

	//For testing purposes
	public static final RegistryObject<TestBlock> BLOCK_TEST = BLOCKS.register("test_test", TestBlock::new);

	public static final RegistryObject<BlockATCController> BLOCK_ATC_CONTROLLER = BLOCKS.register("atc_controller", BlockATCController::new);
}
