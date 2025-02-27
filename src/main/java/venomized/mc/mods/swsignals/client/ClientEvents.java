package venomized.mc.mods.swsignals.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.client.blockentityrenderer.*;

public class ClientEvents {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onEntityRenderRegisterEvent(EntityRenderersEvent.RegisterRenderers event) {
		SwSignal.LOGGER.info("Registering EntityRenderers");
		event.registerBlockEntityRenderer(SwBlockEntities.BE_TWO_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernTwoLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_THREE_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernThreeLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_FOUR_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernFourLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_FIVE_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererModernFiveLightSignalBlockEntity());

		event.registerBlockEntityRenderer(SwBlockEntities.BE_U_SIGN.get(),(ctx)->new BlockEntityRendererUSign());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRegisterAdditionalModelsEvent(ModelEvent.RegisterAdditional e) {
		SwSignal.LOGGER.info("Registering Additional Models");
		e.register(BlockEntityRendererSignal.SIGNAL_LIGHT_MODEL_LOC);

		e.register(BlockEntityRendererModernTwoLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererModernThreeLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererModernFourLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererModernFiveLightSignalBlockEntity.SIGNAL_MODEL_LOC);

		e.register(BlockEntityRendererUSign.MODEL_LOC);

	}

}
