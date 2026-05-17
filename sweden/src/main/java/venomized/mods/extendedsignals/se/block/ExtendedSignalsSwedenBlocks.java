package venomized.mods.extendedsignals.se.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import venomized.mods.extendedsignals.block.BlockRailroadCrossingController;
import venomized.mods.extendedsignals.core.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.crossing.BlockCrossingGate;
import venomized.mods.extendedsignals.se.block.crossing.BlockThreeLightCrossingSignal;

/**
 * Swedish railway content (blocks)
 */
public class ExtendedSignalsSwedenBlocks {
    private static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    // == SWEDISH CONTENT ==
    public static final BlockEntry<BlockSignalBox> BLOCK_SIGNAL_BOX = RegistrateHelper.modelledBlock(registrate(), "signal.se.signal_box", BlockSignalBox::new)
            .register();
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntry<BlockModernTwoLightSignal> BLOCK_TWO_LIGHT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "2l_signal_post_1970", BlockModernTwoLightSignal::new)
            .register();
    public static final BlockEntry<BlockModernThreeLightSignal> BLOCK_THREE_LIGHT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "3l_signal_post_1970", BlockModernThreeLightSignal::new)
            .register();
    public static final NonNullSupplier<? extends Block> BLOCK_FOUR_LIGHT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "4l_signal_post_1970", BlockModernFourLightSignal::new)
            .register();
    public static final BlockEntry<BlockModernFiveLightSignal> BLOCK_FIVE_LIGHT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "5l_signal_post_1970", BlockModernFiveLightSignal::new)
            .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntry<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "3l_distant_signal_post_1970", BlockModernThreeLightDistantSignal::new)
            .register();
    // == DWARF SIGNALS ==
    public static final BlockEntry<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "4l_dwarf_signal_post_1970", BlockModernDwarfSignal::new)
            .register();
    public static final BlockEntry<BlockModernMainDwarfSignal> BLOCK_MODERN_MAIN_DWARF_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "7l_dwarf_main_signal_post_1970", BlockModernMainDwarfSignal::new)
            .register();
    // == MISC SIGNALS ==
    public static final BlockEntry<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "1l_endpoint_post_1920", BlockModernEndpointSignal::new)
            .register();
    // public static final BlockEntry<BlockGenericRotateableBlock> BLOCK_U_SIGN = RegistrateHelper.signalBlock(registrate(),"signal", "se", "u_sign", BlockGenericRotateableBlock::new)
    //         .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntry<BlockRailroadCrossingSignal> BLOCK_RAILROAD_CROSSING_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "1l_railroad_crossing_signal_2_post_1970", BlockRailroadCrossingSignal::new)
            .register();
    public static final BlockEntry<BlockRailroadCrossingDistantSignal> BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL = RegistrateHelper.signalBlock(registrate(),"signal", "se", "3l_distant_railroad_crossing_signal_post_1970", BlockRailroadCrossingDistantSignal::new)
            .register();
    public static final BlockEntry<BlockCrossingGate> BLOCK_CROSSING_GATE = RegistrateHelper.modelledBlock(registrate(),"signal.se.crossing.base", BlockCrossingGate::new)
            .register();
    public static final BlockEntry<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = RegistrateHelper.modelledBlock(registrate(),"signal.se.crossing.controller", BlockRailroadCrossingController::new)
            .register();
    public static final BlockEntry<BlockThreeLightCrossingSignal> BLOCK_THREE_LIGHT_CROSSING_SIGNAL = RegistrateHelper.signalBlock(registrate(),"crossing", "se", "3l_crossing_lights_post_1970", BlockThreeLightCrossingSignal::new)
            .register();

    public static void init() {
    }
}
