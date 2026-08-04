package venomized.mods.extendedsignals.se.blockentity;


import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererCrossingGate;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.util.RegistrateHelper;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.auxilliarysignals.*;
import venomized.mods.extendedsignals.se.block.SwedenBlocks;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityThreeLightCrossingLights;
import venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing.RendererCrossingLights;
import venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing.RendererCrossingSignal;

public final class SwedenBlockEntities {
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntityEntry<BlockEntity2MainSignal> MAIN_2_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "2l_main_signal", BlockEntity2MainSignal::new,
                            SwedenBlocks.MAIN_2_SIGNAL
                    )
                    .renderer(() -> RendererSignal::new)
                    .register();
    //          .register();
    //
    public static final BlockEntityEntry<BlockEntity3MainSignal> MAIN_3_SIGNAL =
            RegistrateHelper.simpleBlockEntity(registrate(), "3l_main_signal", BlockEntity3MainSignal::new, SwedenBlocks.MAIN_3_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntity4CombinedSignal> COMBINED_4_SIGNAL =
            RegistrateHelper.simpleBlockEntity(
                            registrate(), "4_combined_signal", BlockEntity4CombinedSignal::new,
                            SwedenBlocks.COMBINED_4_SIGNAL
                    )
                    .renderer(() -> RendererSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntity5CombinedSignal> COMBINED_5_SIGNAL =
            RegistrateHelper.simpleBlockEntity(registrate(), "5l_combined_signal", BlockEntity5CombinedSignal::new, SwedenBlocks.COMBINED_5_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntityEntry<BlockEntity3DistantSignal> DISTANT_THREE_LIGHT =
            RegistrateHelper.simpleBlockEntity(registrate(), "3l_distant_signal", BlockEntity3DistantSignal::new, SwedenBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    // == DWARF SIGNALS ==
    public static final BlockEntityEntry<BlockEntityDwarfSignal> DWARF_SIGNAL =
            RegistrateHelper.simpleBlockEntity(registrate(), "dwarf_signal", BlockEntityDwarfSignal::new, SwedenBlocks.BLOCK_MODERN_DWARF_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    //
    public static final BlockEntityEntry<BlockEntityMainDwarfSignal> MAIN_DWARF_SIGNAL =
            RegistrateHelper.simpleBlockEntity(registrate(), "main_dwarf_signal", BlockEntityMainDwarfSignal::new, SwedenBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityEndpointSignal> ENDPOINT_SIGNAL =
            RegistrateHelper.simpleBlockEntity(registrate(), "endpoint_signal", BlockEntityEndpointSignal::new, SwedenBlocks.BLOCK_ENDPOINT_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    // == MISC SIGNALS ==
    public static final BlockEntityEntry<BlockEntityRailroadCrossingDistantSignal> RAILROAD_CROSSING_DISTANT_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_distant_signal", BlockEntityRailroadCrossingDistantSignal::new, SwedenBlocks.BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL)
            .register();
    public static final BlockEntityEntry<BlockEntityCrossingSignal> RAILROAD_CROSSING_SIGNAL = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_signal_modern", BlockEntityCrossingSignal::new, SwedenBlocks.CROSSING_SIGNAL)
            .renderer(() -> RendererCrossingSignal::new)
            .register();
    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntityEntry<BlockEntityCrossingGate> CROSSING_GATE = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_gate", BlockEntityCrossingGate::new, SwedenBlocks.BLOCK_CROSSING_GATE)
            .renderer(() -> RendererCrossingGate::new)
            .register();
    public static BlockEntityEntry<BlockEntityThreeLightCrossingLights> CROSSING_LIGHTS = RegistrateHelper
            .simpleBlockEntity(registrate(), "crossing_lights", BlockEntityThreeLightCrossingLights::new, SwedenBlocks.CROSSING_LIGHTS)
            .renderer(() -> RendererCrossingLights::new)
            .register();

    private static Registrate registrate() {
        return ExtendedSignalsSweden.REGISTRATE.get();
    }

    public static void init() {
    }
    //endregion

}