package venomized.mc.mods.swsignals.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntityThreeLightSignalBlock;
import venomized.mc.mods.swsignals.blockentity.BlockEntityTwoLightSignalBlock;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererAbstractSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererThreeLightSignalBlockEntity;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererTwoLightSignalBlockEntity;

public class ClientEvents {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onEntityRenderRegisterEvent(EntityRenderersEvent.RegisterRenderers event) {
		SwSignal.LOGGER.info("Registering EntityRenderers");
		event.registerBlockEntityRenderer(SwBlockEntities.BE_TWO_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererTwoLightSignalBlockEntity());
		event.registerBlockEntityRenderer(SwBlockEntities.BE_THREE_LIGHT_SIGNAL.get(), (ctx)->new BlockEntityRendererThreeLightSignalBlockEntity());
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRegisterAdditionalModelsEvent(ModelEvent.RegisterAdditional e) {
		SwSignal.LOGGER.info("Registering Additional Models");
		e.register(BlockEntityRendererAbstractSignal.SIGNAL_LIGHT_MODEL_LOC);

		e.register(BlockEntityRendererTwoLightSignalBlockEntity.SIGNAL_MODEL_LOC);
		e.register(BlockEntityRendererThreeLightSignalBlockEntity.SIGNAL_MODEL_LOC);

	}


}
