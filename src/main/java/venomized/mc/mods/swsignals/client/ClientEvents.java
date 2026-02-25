package venomized.mc.mods.swsignals.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import venomized.mc.mods.swsignals.block.se.SeModels;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.RendererSignal;
import venomized.mc.mods.swsignals.client.ui.ScreenTest;
import venomized.mc.mods.swsignals.client.ui.overlays.ATCOverlayHUD;
import venomized.mc.mods.swsignals.core.SwSignal;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = SwSignal.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    private static final ArrayList<ModelResourceLocation> externalModelAssetsPaths = new ArrayList<>();

    public static void registerModelWithExternalAssets(ModelResourceLocation... assets) {
        externalModelAssetsPaths.addAll(List.of(assets));
    }

    private static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, path);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SeModels.init();
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(SwMenus.MENU_TEST.get(), ScreenTest::new);
    }

    @SubscribeEvent
    public static void onEntityRenderRegisterEvent(EntityRenderersEvent.RegisterRenderers event) {
        SwSignal.LOGGER.info("Registering EntityRenderers");
        //region Swedish block signals
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_TWO_LIGHT_SIGNAL.get(), (ctx) -> new BlockEntityRendererSignal());
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_THREE_LIGHT_SIGNAL.get(), (ctx) -> new BlockEntityRendererSignal());
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.get(), (ctx) -> new BlockEntityRendererModernFourLightSignal());
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_FIVE_LIGHT_SIGNAL.get(), (ctx) -> new BlockEntityRendererModernFiveLightSignal());
        // //endregion
        // //region Swedish Distant signals
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_THREE_LIGHT_DISTANT_SIGNAL.get(), (ctx) -> new BlockEntityRendererModernThreeLightDistantSignal());
        // //endregion
        // //region Swedish dwarf signals
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_DWARF_SIGNAL.get(), (ctx) -> new BlockEntityRendererModernDwarfSignal());
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_MAIN_DWARF_SIGNAL.get(), (ctx) -> new BlockEntityRendererModernMainDwarfSignal());
        // //endregion
        // //region Swedish railroad crossing material
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_RAILROAD_CROSSING_SIGNAL.get(), (ctx) -> new BlockEntityRendererRailroadCrossingSignal());
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_RAILROAD_CROSSING_DISTANT_SIGNAL.get(), (ctx) -> new BlockEntityRendererRailroadCrossingDistantSignal());
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_CROSSING_GATE.get(), (ctx) -> new BlockEntityRendererCrossingGate());
        // //endregion
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_U_SIGN.get(), (ctx) -> new BlockEntityRendererGeneric());
//
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_ATC_CONTROLLER.get(), BlockEntityRendererATCController::new);
//
        // event.registerBlockEntityRenderer(SwBlockEntities.BE_ENDPOINT_SIGNAL.get(), (ctx) -> new BlockEntityRendererEndpointSignal());
    }

    @SubscribeEvent
    public static void onScreenOverlay(RegisterGuiLayersEvent e) {
        e.registerAbove(VanillaGuiLayers.HOTBAR, ATCOverlayHUD.ATC_OVERLAY, new ATCOverlayHUD());
    }

    @SubscribeEvent
    public static void onRegisterAdditionalModelsEvent(ModelEvent.RegisterAdditional e) {
        SwSignal.LOGGER.info("Registering Additional Models");
        e.register(RendererSignal.SIGNAL_LIGHT_MODEL_LOC);

        e.register(ModelResourceLocation.standalone(SwSignal.resource("block/tracks/se_balise")));

        // e.register(SwSignal.modLoc(BlockEntityRendererCrossingGate.ARM_5));

        // what an ugly way to do this
        for (ModelResourceLocation loc : externalModelAssetsPaths) {
            e.register(loc);
        }

        // e.register(BlockEntityRendererModernTwoLightSignal.SIGNAL_MODEL_LOC);
        // e.register(BlockEntityRendererModernThreeLightSignal.SIGNAL_MODEL_LOC);
        // e.register(BlockEntityRendererModernFourLightSignal.SIGNAL_MODEL_LOC);
        // e.register(BlockEntityRendererModernFiveLightSignal.SIGNAL_MODEL_LOC);
//
        // e.register(modLoc("block/sw_4l_dwarf_signal_post_1970"));
//
        // e.register(BlockEntityRendererEndpointSignal.SIGNAL_MODEL_LOC);
//
        // e.register(modLoc("block/sw_3l_distant_signal_post_1970"));
//
        // e.register(modLoc("block/signals/se/sw_railroadcrossing_signal_2"));
        // e.register(modLoc("block/sw_3l_distant_railroad_crossing_signal"));
//
        // e.register(modLoc("block/sw_u_sign"));
    }
}
