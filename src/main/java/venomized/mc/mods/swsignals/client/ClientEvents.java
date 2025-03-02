package venomized.mc.mods.swsignals.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.client.blockentityrenderer.sw.*;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
	private static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, path);
	}

	@SubscribeEvent
	public void onEntityRenderRegisterEvent(EntityRenderersEvent.RegisterRenderers event) {
		SwSignal.LOGGER.info("Registering EntityRenderers");
		event.registerBlockEntityRenderer(SwBlockEntities.BE_TWO_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernTwoLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_THREE_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernThreeLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernFourLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_FIVE_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernFiveLightSignalBlockEntity());

		event.registerBlockEntityRenderer(SwBlockEntities.BE_ENDPOINT_SIGNAL.get(), (ctx)->new BlockEntityRendererEndpointSignal());

		event.registerBlockEntityRenderer(SwBlockEntities.BE_THREE_LIGHT_DISTANT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernThreeLightDistantSignalBlockEntity());

		event.registerBlockEntityRenderer(SwBlockEntities.BE_U_SIGN.get(),(ctx)->new BlockEntityRendererGeneric("block/sw_u_sign"));
	}

	@SubscribeEvent
	public void onRegisterAdditionalModelsEvent(ModelEvent.RegisterAdditional e) {
		SwSignal.LOGGER.info("Registering Additional Models");
		e.register(BlockEntityRendererSignal.SIGNAL_LIGHT_MODEL_LOC);

		e.register(BlockEntityRendererModernTwoLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererModernThreeLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererModernFourLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererModernFiveLightSignalBlockEntity.SIGNAL_MODEL_LOC);

		e.register(BlockEntityRendererEndpointSignal.SIGNAL_MODEL_LOC);

		e.register(modLoc("block/sw_3l_distant_signal_post_1970"));

		e.register(modLoc("block/sw_u_sign"));
	}
}
