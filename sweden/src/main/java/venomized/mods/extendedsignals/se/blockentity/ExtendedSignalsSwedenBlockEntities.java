package venomized.mods.extendedsignals.se.blockentity;


import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.auxilliarysignals.*;
import venomized.mods.extendedsignals.se.block.ExtendedSignalsSwedenBlocks;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityThreeLightCrossingLights;
import venomized.mods.extendedsignals.se.client.blockentityrenderer.se.*;
import venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing.RendererCrossingGate;
import venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing.RendererThreeLightCrossingLights;

public final class ExtendedSignalsSwedenBlockEntities {
    private static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    public static final BlockEntityEntry<BlockEntitySignalBox> SE_SIGNAL_BOX =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_signal_box", BlockEntitySignalBox::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_SIGNAL_BOX
                    )
                    .renderer(() -> RendererGeneric::new)
                    .register();

    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntityEntry<BlockEntityMainSignal> MAIN_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "main_signal", BlockEntityMainSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_TWO_LIGHT_SIGNAL,
                            ExtendedSignalsSwedenBlocks.BLOCK_THREE_LIGHT_SIGNAL
                    )
                    .renderer(() -> RendererSignal::new)
                    .register();
    //          .register();
    //
    // // public static final BlockEntityEntry<BlockEntityMainSignal> MAIN_THREE_SIGNAL =
    // //         RegistrateHelper.simpleBlockEntity(registrate(),"se_3l_signal", BlockEntityMainSignal::new, ExtendedSignalsSwedenBlocks.BLOCK_THREE_LIGHT_SIGNAL)
    // //                 .renderer(() -> RendererSignal::new)
    // //                 .register();
    //
    public static final BlockEntityEntry<BlockEntityCombinedSignal> COMBINED_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_combined_signal", BlockEntityCombinedSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_FOUR_LIGHT_SIGNAL,
                            ExtendedSignalsSwedenBlocks.BLOCK_FIVE_LIGHT_SIGNAL
                    )
                    .renderer(() -> RendererSignal::new)
                    .register();
    //
    // public static final BlockEntityEntry<BlockEntityCombinedSignal> COMBINED_FIVE_SIGNAL =
    //        RegistrateHelper.simpleBlockEntity(registrate(),"se_5l_signal", BlockEntityCombinedSignal::new, ExtendedSignalsSwedenBlocks.BLOCK_FIVE_LIGHT_SIGNAL)
    //                .renderer(() -> RendererSignal::new)
    //                .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntityEntry<BlockEntityThreeLightDistantSignal> DISTANT_THREE_LIGHT =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_3l_distant_signal", BlockEntityThreeLightDistantSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL
                    )
                    .renderer(() -> RendererThreeLightDistantSignal::new)
                    .register();
    // == DWARF SIGNALS ==
    public static final BlockEntityEntry<BlockEntityDwarfSignal> DWARF_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_dwarf_signal", BlockEntityDwarfSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_MODERN_DWARF_SIGNAL
                    )
                    .renderer(() -> RendererDwarfSignal::new)
                    .register();
    //
    public static final BlockEntityEntry<BlockEntityMainDwarfSignal> MAIN_DWARF_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_main_dwarf_signal", BlockEntityMainDwarfSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL
                    )
                    .renderer(() -> RendererMainDwarfSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityEndpointSignal> ENDPOINT_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_1l_endpoint_signal", BlockEntityEndpointSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_ENDPOINT_SIGNAL
                    )
                    .renderer(() -> RendererEndpointSignal::new)
                    .register();
    // public static final BlockEntityEntry<BlockEntityUSign> U_SIGN =
    //         ExtendedSignalsAllBlockEntities.simpleBlockEntity("se_u_sign", BlockEntityUSign::new, SeBlocks.BLOCK_U_SIGN)
    //                 .renderer(() -> RendererGeneric::new)
    //                 .register();

    // == MISC SIGNALS ==
    public static final BlockEntityEntry<BlockEntityRailroadCrossingSignal> RAILROAD_CROSSING_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_railroad_crossing_signal", BlockEntityRailroadCrossingSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL
                    )
                    .renderer(() -> RendererRailroadCrossingSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityRailroadCrossingDistantSignal> RAILROAD_CROSSING_DISTANT_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_railroad_crossing_distant_signal", BlockEntityRailroadCrossingDistantSignal::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL
                    )
                    .renderer(() -> RendererRailroadCrossingDistantSignal::new)
                    .register();

    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntityEntry<BlockEntityCrossingGate> CROSSING_GATE =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_crossing_gate", BlockEntityCrossingGate::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_CROSSING_GATE
                    )
                    .renderer(() -> RendererCrossingGate::new)
                    .register();
    // TESTING AREA
    public static BlockEntityEntry<BlockEntityThreeLightCrossingLights> THREE_LIGHT_CROSSING_LIGHT_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "se_3l_crossing_signal", BlockEntityThreeLightCrossingLights::new,
                            ExtendedSignalsSwedenBlocks.BLOCK_THREE_LIGHT_CROSSING_SIGNAL
                    )
                    .renderer(() -> RendererThreeLightCrossingLights::new)
                    .register();

    public static void init() {
    }
    //endregion

}