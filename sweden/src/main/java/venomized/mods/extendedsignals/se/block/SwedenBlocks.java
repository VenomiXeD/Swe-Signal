package venomized.mods.extendedsignals.se.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import venomized.mods.extendedsignals.core.block.BlockModelled;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.block.crossing.BlockCrossingGate;
import venomized.mods.extendedsignals.se.block.crossing.BlockCrossingLights;

/**
 * Swedish railway content (blocks)
 */
public final class SwedenBlocks {
    // == SWEDISH CONTENT ==
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntry<Block2SignalModern> SIGNAL_MAIN_2_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "2l_signal_modern", Block2SignalModern::new)
            .register();
    public static final BlockEntry<Block3SignalModern> SIGNAL_MAIN_3_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "3l_signal_modern", Block3SignalModern::new)
            .register();

    public static final BlockEntry<Block4CombinedSignal> SIGNAL_COMBINED_4_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "4l_signal_modern", Block4CombinedSignal::new)
            .register();

    public static final BlockEntry<Block5CombinedSignal> SIGNAL_COMBINED_5_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "5l_signal_modern", Block5CombinedSignal::new)
            .register();

    // == DISTANT SIGNALS ==
    public static final BlockEntry<BlockModernThreeLightDistantSignal> SIGNAL_DISTANT_3_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "3l_distant_signal_modern", BlockModernThreeLightDistantSignal::new)
            .register();

    // == DWARF SIGNALS ==
    public static final BlockEntry<BlockModernDwarfSignal> SIGNAL_DWARF_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "dwarf_signal_modern", BlockModernDwarfSignal::new)
            .register();

    public static final BlockEntry<BlockModernMainDwarfSignal> SIGNAL_MAIN_DWARF_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "dwarf_main_signal_modern", BlockModernMainDwarfSignal::new)
            .register();

    // == MISC SIGNALS ==
    public static final BlockEntry<BlockModernEndpointSignal> SIGNAL_ENDPOINT = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "endpoint_signal_modern", BlockModernEndpointSignal::new)
            .register();

    // public static final BlockEntry<BlockGenericRotateableBlock> BLOCK_U_SIGN = RegistrateHelper.signalBlock(registrate(),"signals", "se", "u_sign", BlockGenericRotateableBlock::new)
    //         .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntry<BlockCrossingDistantSignal> SIGNAL_CROSSING_DISTANT_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "crossing_distant_signal_modern", BlockCrossingDistantSignal::new)
            .register();

    public static final BlockEntry<BlockCrossingSignal> SIGNAL_CROSSING_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signals", "crossing_signal_modern", BlockCrossingSignal::new)
            .register();

    public static final BlockEntry<BlockCrossingGate> CROSSING_GATE_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "crossings", "base", BlockCrossingGate::new)
            .register();

    public static final BlockEntry<BlockCrossingLights> CROSSING_LIGHTS_MODERN = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "crossings", "crossing_lights_modern", BlockCrossingLights::new)
            .register();

    // == MISC ==
    public static final BlockEntry<BlockModelled> SIGN_CONTINUED_DRIVING_PERMISSION = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signs", "continued_driving_permission", BlockModelled::withBlockEntity, ModelBlockEntity::new)
            .register();


    public static final BlockEntry<BlockModelled> SIGN_PLOW_RAISE = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signs", "plow_raise", BlockModelled::withBlockEntity, ModelBlockEntity::new)
            .register();


    public static final BlockEntry<BlockModelled> SIGN_SPEED = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signs", "speed_sign", BlockModelled::withBlockEntity, ModelBlockEntity::new)
            .register();

    public static final BlockEntry<BlockModelled> SIGN_WEIGHT = RegistrateHelper
            .genericCustomModelledBlock(registrate(), "se", "signs", "weight_signal", BlockModelled::withBlockEntity, ModelBlockEntity::new)
            .register();

    private static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    public static void init() {
    }
}
