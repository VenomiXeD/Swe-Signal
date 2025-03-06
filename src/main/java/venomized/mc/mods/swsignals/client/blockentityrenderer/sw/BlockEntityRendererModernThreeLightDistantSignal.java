package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityThreeLightDistantSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

public class BlockEntityRendererModernThreeLightDistantSignal extends BlockEntityRendererBase<BlockEntityThreeLightDistantSignal> {
	/**
	 * @param pBlockEntity
	 * @param pPartialTick
	 * @param pPoseStack
	 * @param pBuffer
	 * @param pPackedLight
	 * @param pPackedOverlay
	 */
	@Override
	public void render(BlockEntityThreeLightDistantSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
		getRenderer().tesselateWithAO(
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

		// Center light in proper position
		pPoseStack.translate(.5f, 1 / 16f, 8f / 16f);
		pPoseStack.translate(0f, 1.9f / 16f, -5.6f / 16f);

		SwedishSignalAspect aspect = pBlockEntity.getCurrentDisplayingAspect();
		pBlockEntity.stepSignalLighting(pPartialTick, aspect, pBlockEntity.getCurrentDisplayingState(), !pBlockEntity.valid() || aspect == null);

		pPoseStack.translate(0, 6.5 / 16f * 2, 0);
		for (int i = 0; i < 3; i++) {

			float r = 0;
			float g = 0;
			float b = 0;
			if (i == 1) {
				float w = pBlockEntity.lightLevels[i + 2];
				r = w;
				g = w;
				b = w;
			} else {
				g = pBlockEntity.lightLevels[i + 2];
			}
			pPoseStack.pushPose();
			pPoseStack.scale(1.1f, 1.1f, 0f);
			getRenderer().renderModel(
					pPoseStack.last(),
					pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
					pBlockEntity.getBlockState(),
					BlockEntityRendererSignal.signalLightModel(),
					r, g, b,
					BlockEntityRendererSignal.FULLBRIGHT,
					pPackedOverlay
			);
			pPoseStack.popPose();
			pPoseStack.translate(0, -6.5 / 16f, 0);
		}
	}
}
