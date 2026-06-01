package venomized.mods.extendedsignals.se.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.crossing.BlockCrossingGate;
import venomized.mods.extendedsignals.se.block.crossing.BlockCrossingLights;

/**
 * Swedish railway content (blocks)
 */
public class SwedenBlocks {
    // == SWEDISH CONTENT ==
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntry<Block2SignalModern> MAIN_2_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "2l_signal_modern", Block2SignalModern::new)
            .register();
    public static final BlockEntry<Block3SignalModern> MAIN_3_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "3l_signal_modern", Block3SignalModern::new)
            .register();

    public static final BlockEntry<Block4CombinedSignal> COMBINED_4_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "4l_signal_modern", Block4CombinedSignal::new)
            .register();

    public static final BlockEntry<Block5CombinedSignal> COMBINED_5_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "5l_signal_modern", Block5CombinedSignal::new)
            .register();

    // == DISTANT SIGNALS ==
    public static final BlockEntry<BlockModernThreeLightDistantSignal> BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "3l_distant_signal_modern", BlockModernThreeLightDistantSignal::new)
            .register();

    // == DWARF SIGNALS ==
    public static final BlockEntry<BlockModernDwarfSignal> BLOCK_MODERN_DWARF_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "dwarf_signal_modern", BlockModernDwarfSignal::new)
            .register();

    public static final BlockEntry<BlockModernMainDwarfSignal> BLOCK_MODERN_MAIN_DWARF_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "dwarf_main_signal_modern", BlockModernMainDwarfSignal::new)
            .register();

    // == MISC SIGNALS ==
    public static final BlockEntry<BlockModernEndpointSignal> BLOCK_ENDPOINT_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "endpoint_signal_modern", BlockModernEndpointSignal::new)
            .register();

    // public static final BlockEntry<BlockGenericRotateableBlock> BLOCK_U_SIGN = RegistrateHelper.signalBlock(registrate(),"signals", "se", "u_sign", BlockGenericRotateableBlock::new)
    //         .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntry<BlockCrossingSignal> CROSSING_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "crossings", "crossing_signal_modern", BlockCrossingSignal::new)
            .register();

    public static final BlockEntry<BlockCrossingDistantSignal> BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "crossings", "crossing_distant_signal_modern", BlockCrossingDistantSignal::new)
            .register();

    public static final BlockEntry<BlockCrossingGate> BLOCK_CROSSING_GATE = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "crossings", "base", BlockCrossingGate::new)
            .register();

    public static final BlockEntry<BlockCrossingLights> BLOCK_THREE_LIGHT_CROSSING_SIGNAL = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "crossings", "crossing_lights_modern", BlockCrossingLights::new)
            .register();

    private static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    public static void init() {
    }
}
