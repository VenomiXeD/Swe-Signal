package venomized.mc.mods.swsignals.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.*;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.crossing.BlockEntityRendererCrossingGate;
import venomized.mc.mods.swsignals.client.ui.ScreenTest;
import venomized.mc.mods.swsignals.client.ui.overlays.ATCOverlayHUD;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
	private static final ArrayList<ResourceLocation> externalModelAssetsPaths = new ArrayList<>();

	public static void registerModelWithExternalAssets(ResourceLocation... assets) {
		externalModelAssetsPaths.addAll(List.of(assets));
	}

	private static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, path);
	}

	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(SwMenus.MENU_TEST.get(), ScreenTest::new);
		});
	}

	@SubscribeEvent
	public void onEntityRenderRegisterEvent(EntityRenderersEvent.RegisterRenderers event) {
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
	public void onScreenOverlay(RegisterGuiOverlaysEvent e) {
		e.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "atc_overlay", ATCOverlayHUD::render);
	}

	@SubscribeEvent
	public void onRegisterAdditionalModelsEvent(ModelEvent.RegisterAdditional e) {
		SwSignal.LOGGER.info("Registering Additional Models");
		e.register(RendererSignal.SIGNAL_LIGHT_MODEL_LOC);

		e.register(SwSignal.modLoc("block/tracks/se_balise"));

		e.register(SwSignal.modLoc(BlockEntityRendererCrossingGate.ARM_5));

		// what an ugly way to do this
		for (ResourceLocation loc : externalModelAssetsPaths) {
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
