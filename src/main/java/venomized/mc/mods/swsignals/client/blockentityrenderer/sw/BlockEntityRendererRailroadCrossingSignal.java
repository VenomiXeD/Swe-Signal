package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals.BlockEntityRailroadCrossingSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class BlockEntityRendererRailroadCrossingSignal extends BlockEntityRendererBase<BlockEntityRailroadCrossingSignal> {
	/**
	 * @param pBlockEntity
	 * @param pPartialTick
	 * @param pPoseStack
	 * @param pBuffer
	 * @param pPackedLight
	 * @param pPackedOverlay
	 */
	@Override
	public void render(BlockEntityRailroadCrossingSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		getRenderer()
				.tesselateWithAO(
						pBlockEntity.getLevel(),
						getModel(pBlockEntity.getBlockState()),
						pBlockEntity.getBlockState(),
						pBlockEntity.getBlockPos(),
						pPoseStack,
						pBuffer.getBuffer(RenderType.solid()),
						true,
						pBlockEntity.getLevel().getRandom(),
						pPackedLight,
						pPackedOverlay
				);

		pPoseStack.translate(.5f,1 + 1.5/16f,5.6/16f);

		pPoseStack.pushPose();
		pPoseStack.scale(1.1f,1.1f,0);
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				1,  pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0, pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.translate(0,0,9.8/16f);

		pPoseStack.pushPose();
		pPoseStack.scale(1.1f,1.1f,0);
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				1,  pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0, pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();
	}
}
