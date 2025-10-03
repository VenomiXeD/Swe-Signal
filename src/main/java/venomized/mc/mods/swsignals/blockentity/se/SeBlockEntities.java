package venomized.mc.mods.swsignals.blockentity.se;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mc.mods.swsignals.block.se.SeBlocks;
import venomized.mc.mods.swsignals.blockentity.AllBlockEntities;
import venomized.mc.mods.swsignals.blockentity.BlockEntityATCController;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingController;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.*;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityCrossingGate;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityThreeLightCrossingLights;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntityFiveLightSignal;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntityFourLightSignal;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntityThreeLightSignal;
import venomized.mc.mods.swsignals.blockentity.se.mainsignals.BlockEntityTwoLightSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.RendererATCController;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.*;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.crossing.RendererCrossingGate;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.crossing.RendererThreeLightCrossingLights;

public final class SeBlockEntities {
    public static final BlockEntityEntry<BlockEntityRailroadCrossingController> BE_SE_RAILROAD_CROSSING_CONTROLLER =
            AllBlockEntities.simpleBlockEntity(
                    "be_se_crossing_controller",
                    BlockEntityRailroadCrossingController::new,
                    SeBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER
            ).register();
    // public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SwSignal.MOD_ID);
    //region SWEDISH CONTENT
    public static final BlockEntityEntry<BlockEntitySignalBox> BE_SE_SIGNAL_BOX =
            AllBlockEntities.simpleBlockEntity("be_se_signal_box", BlockEntitySignalBox::new, SeBlocks.BLOCK_SIGNAL_BOX)
                    .renderer(() -> RendererGeneric::new)
                    .register();

    // public static final RegistryObject<BlockEntityType<BlockEntityRailroadCrossingController>> BE_RAILROAD_CROSSING_CONTROLLER =
    // 		BLOCK_ENTITIES.register("be_railroad_crossing_controller", () -> BlockEntityType.Builder.of(BlockEntityRailroadCrossingController::new,
    // 				SeBlocks.BLOCK_RAILROAD_CROSSING_CONTROLLER.get()
    // 		).build(null));
    // == SIGNALS FROM 2-5 LIGHTS ==
    public static final BlockEntityEntry<BlockEntityTwoLightSignal> BE_TWO_LIGHT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_2l_signal", BlockEntityTwoLightSignal::new, SeBlocks.BLOCK_TWO_LIGHT_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityThreeLightSignal> BE_THREE_LIGHT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_3l_signal", BlockEntityThreeLightSignal::new, SeBlocks.BLOCK_THREE_LIGHT_SIGNAL)
                    .renderer(() -> RendererSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityFourLightSignal> BE_FOUR_LIGHT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_4l_signal", BlockEntityFourLightSignal::new, SeBlocks.BLOCK_FOUR_LIGHT_SIGNAL)
                    .renderer(() -> RendererModernFourLightSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityFiveLightSignal> BE_FIVE_LIGHT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_5l_signal", BlockEntityFiveLightSignal::new, SeBlocks.BLOCK_FIVE_LIGHT_SIGNAL)
                    .renderer(() -> RendererModernFiveLightSignal::new)
                    .register();
    // == DISTANT SIGNALS ==
    public static final BlockEntityEntry<BlockEntityThreeLightDistantSignal> BE_THREE_LIGHT_DISTANT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_3l_distant_signal", BlockEntityThreeLightDistantSignal::new, SeBlocks.BLOCK_MODERN_THREE_LIGHT_DISTANT_SIGNAL)
                    .renderer(() -> RendererThreeLightDistantSignal::new)
                    .register();
    // == DWARF SIGNALS ==
    public static final BlockEntityEntry<BlockEntity> BE_DWARF_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_dwarf_signal", BlockEntityDwarfSignal::new, SeBlocks.BLOCK_MODERN_DWARF_SIGNAL)
                    .renderer(() -> RendererBasicDwarfSignal::new)
                    .register();
    //
    public static final BlockEntityEntry<BlockEntity> BE_MAIN_DWARF_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_main_dwarf_signal", BlockEntityMainDwarfSignal::new, SeBlocks.BLOCK_MODERN_MAIN_DWARF_SIGNAL)
                    .renderer(() -> RendererMainDwarfSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityEndpointSignal> BE_ENDPOINT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_1l_endpoint_signal", BlockEntityEndpointSignal::new, SeBlocks.BLOCK_ENDPOINT_SIGNAL)
                    .renderer(() -> RendererEndpointSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityUSign> BE_U_SIGN =
            AllBlockEntities.simpleBlockEntity("be_se_u_sign", BlockEntityUSign::new, SeBlocks.BLOCK_U_SIGN)
                    .renderer(() -> RendererGeneric::new)
                    .register();

    // == MISC SIGNALS ==
    public static final BlockEntityEntry<BlockEntityRailroadCrossingSignal> BE_RAILROAD_CROSSING_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_railroad_crossing_signal", BlockEntityRailroadCrossingSignal::new, SeBlocks.BLOCK_RAILROAD_CROSSING_SIGNAL)
                    .renderer(() -> RendererRailroadCrossingSignal::new)
                    .register();
    public static final BlockEntityEntry<BlockEntityRailroadCrossingDistantSignal> BE_RAILROAD_CROSSING_DISTANT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_railroad_crossing_distant_signal", BlockEntityRailroadCrossingDistantSignal::new, SeBlocks.BLOCK_RAILROAD_CROSSING_DISTANT_SIGNAL)
                    .renderer(() -> RendererRailroadCrossingDistantSignal::new)
                    .register();

    // == RAILROAD CROSSING SIGNALS ==
    public static final BlockEntityEntry<BlockEntityCrossingGate> BE_CROSSING_GATE =
            AllBlockEntities.simpleBlockEntity("be_se_crossing_gate", BlockEntityCrossingGate::new, SeBlocks.BLOCK_CROSSING_GATE)
                    .renderer(() -> RendererCrossingGate::new)
                    .register();
    // TESTING AREA
    public static final BlockEntityEntry<BlockEntityATCController> BE_ATC_CONTROLLER =
            AllBlockEntities.simpleBlockEntity("be_se_atc_controller", BlockEntityATCController::new, SeBlocks.BLOCK_ATC_CONTROLLER)
                    .renderer(() -> RendererATCController::new)
                    .register();
    public static BlockEntityEntry<BlockEntityThreeLightCrossingLights> BE_THREE_LIGHT_CROSSING_LIGHT_SIGNAL =
            AllBlockEntities.simpleBlockEntity("be_se_3l_crossing_signal", BlockEntityThreeLightCrossingLights::new, SeBlocks.BLOCK_THREE_LIGHT_CROSSING_SIGNAL)
                    .renderer(() -> RendererThreeLightCrossingLights::new)
                    .register();

    public static void init() {
    }
    //endregion

}