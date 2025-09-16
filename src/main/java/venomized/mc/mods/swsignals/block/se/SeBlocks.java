package venomized.mc.mods.swsignals.block.se;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.BlockATCController;
import venomized.mc.mods.swsignals.block.BlockRailroadCrossingController;
import venomized.mc.mods.swsignals.block.SwBlocks;
import venomized.mc.mods.swsignals.block.se.crossing.BlockCrossingGate;

public class SeBlocks {
    // == SWEDISH CONTENT ==
    public static final BlockEntry<BlockSignalBox> BLOCK_SIGNAL_BOX = SwBlocks.modelledBlock("signals.se.signal_box", BlockSignalBox::new)
            //.simpleItem()
            .register();
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntry<BlockModernTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = SwBlocks.modelledBlock("signals.se.2l_signal_post_1970", BlockModernTwoLightSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockModernThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = SwBlocks.modelledBlock("signals.se.3l_signal_post_1970", BlockModernThreeLightSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockModernFourLightSignal> BLOCK_FOUR_LIGHT_SIGNAL = SwBlocks.modelledBlock("signals.se.4l_signal_post_1970", BlockModernFourLightSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockModernFiveLightSignal> BLOCK_FIVE_LIGHT_SIGNAL = SwBlocks.modelledBlock("signals.se.5l_signal_post_1970", BlockModernFiveLightSignal::new)
            //.simpleItem()
            .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntry<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = SwBlocks.modelledBlock("signals.se.3l_distant_signal_post_1970", BlockModernThreeLightDistantSignal::new)
            //.simpleItem()
            .register();
    // == DWARF SIGNALS ==
    public static final BlockEntry<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = SwBlocks.modelledBlock("signals.se.4l_dwarf_signal_post_1970", BlockModernDwarfSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockModernMainDwarfSignal> BLOCK_MODERN_MAIN_DWARF_SIGNAL = SwBlocks.modelledBlock("signals.se.7l_dwarf_main_signal_post_1970", BlockModernMainDwarfSignal::new)
            //.simpleItem()
            .register();
    // == MISC SIGNALS ==
    public static final BlockEntry<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = SwBlocks.modelledBlock("signals.se.1l_signal_endpoint_post_1920", BlockModernEndpointSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockGeneric45DegreeBlock> BLOCK_U_SIGN = SwBlocks.modelledBlock("signals.se.u_sign", BlockGeneric45DegreeBlock::new)
            //.simpleItem()
            .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntry<BlockRailroadCrossingSignal> BLOCK_RAILROAD_CROSSING_SIGNAL = SwBlocks.modelledBlock("signals.se.1l_railroad_crossing_signal_2", BlockRailroadCrossingSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockRailroadCrossingDistantSignal> BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL = SwBlocks.modelledBlock("signals.se.3l_distant_railroad_crossing_signal", BlockRailroadCrossingDistantSignal::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockCrossingGate> BLOCK_CROSSING_GATE = SwBlocks.modelledBlock("signals.se.crossing.base", BlockCrossingGate::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = SwBlocks.modelledBlock("signals.se.crossing.controller", BlockRailroadCrossingController::new)
            //.simpleItem()
            .register();
    public static final BlockEntry<BlockATCController> BLOCK_ATC_CONTROLLER = SwSignal.REGISTRATE.get().block("atc_controller", BlockATCController::new)
            .properties(p -> BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
            //.simpleItem()
            .register();

    public static void init() {
    }
}
