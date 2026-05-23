package venomized.mods.extendedsignals.se.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import venomized.mods.extendedsignals.core.block.BlockRailroadCrossingController;
import venomized.mods.extendedsignals.core.block.BlockSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.crossing.BlockCrossingGate;
import venomized.mods.extendedsignals.se.block.crossing.BlockThreeLightCrossingSignal;

/**
 * Swedish railway content (blocks)
 */
public class ExtendedSignalsSwedenBlocks {
    // == SWEDISH CONTENT ==
    public static final BlockEntry<BlockSignalBox> BLOCK_SIGNAL_BOX = RegistrateHelper.modelledBlock(registrate(), "signal.se.signal_box", BlockSignalBox::new)
            .register();
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntry<BlockSignal> BLOCK_TWO_LIGHT_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "2l_signal_post_1970",
                    BlockSignal.generic(2, BlockSignal.SignalType.MAIN)
            )
            .register();
    public static final BlockEntry<BlockSignal> BLOCK_THREE_LIGHT_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "3l_signal_post_1970", BlockSignal.generic(3, BlockSignal.SignalType.MAIN))
            .register();
    public static final BlockEntry<BlockSignal> BLOCK_FOUR_LIGHT_SIGNAL = RegistrateHelper.genericCombinedSignalBlock(
                    registrate(), "signals", "se", "4l_signal_post_1970",
                    BlockSignal.generic(4, BlockSignal.SignalType.COMBINED)
            )
            .register();
    public static final BlockEntry<BlockSignal> BLOCK_FIVE_LIGHT_SIGNAL = RegistrateHelper.genericCombinedSignalBlock(
                    registrate(), "signals", "se", "5l_signal_post_1970",
                    BlockSignal.generic(5, BlockSignal.SignalType.COMBINED)
            )
            .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntry<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "3l_distant_signal_post_1970", BlockModernThreeLightDistantSignal::new)
            .register();
    // == DWARF SIGNALS ==
    public static final BlockEntry<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "4l_dwarf_signal_post_1970", BlockModernDwarfSignal::new)
            .register();
    public static final BlockEntry<BlockModernMainDwarfSignal> BLOCK_MODERN_MAIN_DWARF_SIGNAL =
            RegistrateHelper
                    .genericMainSignalBlock(
                            registrate(),
                            "signals",
                            "se",
                            "7l_dwarf_main_signal_post_1970",
                            BlockModernMainDwarfSignal::new
                    )
            .register();
    // == MISC SIGNALS ==
    public static final BlockEntry<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "1l_endpoint_post_1920", BlockModernEndpointSignal::new)
            .register();
    // public static final BlockEntry<BlockGenericRotateableBlock> BLOCK_U_SIGN = RegistrateHelper.signalBlock(registrate(),"signals", "se", "u_sign", BlockGenericRotateableBlock::new)
    //         .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntry<BlockRailroadCrossingSignal> BLOCK_RAILROAD_CROSSING_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "1l_railroad_crossing_signal_2_post_1970", BlockRailroadCrossingSignal::new)
            .register();
    public static final BlockEntry<BlockRailroadCrossingDistantSignal> BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "signals", "se", "3l_distant_railroad_crossing_signal_post_1970",
                    BlockRailroadCrossingDistantSignal::new
            )
            .register();
    public static final BlockEntry<BlockCrossingGate> BLOCK_CROSSING_GATE = RegistrateHelper.modelledBlock(registrate(), "signal.se.crossing.base", BlockCrossingGate::new)
            .register();
    public static final BlockEntry<BlockRailroadCrossingController> BLOCK_RAILROAD_CROSSING_CONTROLLER = RegistrateHelper.modelledBlock(registrate(), "signal.se.crossing.controller", BlockRailroadCrossingController::new)
            .register();
    public static final BlockEntry<BlockThreeLightCrossingSignal> BLOCK_THREE_LIGHT_CROSSING_SIGNAL = RegistrateHelper.genericMainSignalBlock(
                    registrate(), "crossing", "se", "3l_crossing_lights_post_1970", BlockThreeLightCrossingSignal::new)
            .register();

    private static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    public static void init() {
    }
}
