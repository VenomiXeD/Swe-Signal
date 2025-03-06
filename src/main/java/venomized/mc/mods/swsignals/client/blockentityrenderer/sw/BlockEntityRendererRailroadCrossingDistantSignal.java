package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import venomized.mc.mods.swsignals.blockentity.sw.auxilliarysignals.BlockEntityRailroadCrossingDistantSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class BlockEntityRendererRailroadCrossingDistantSignal extends BlockEntityRendererBase<BlockEntityRailroadCrossingDistantSignal> {
	/**
	 * @param pBlockEntity
	 * @param pPartialTick
	 * @param pPoseStack
	 * @param pBuffer
	 * @param pPackedLight
	 * @param pPackedOverlay
	 */
	@Override
	public void render(BlockEntityRailroadCrossingDistantSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
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

		if (pBlockEntity.blink() || pBlockEntity.isRailroadCrossingControllerPowered()) {
			pBlockEntity.lightLevel = Math.min(1,  pBlockEntity.lightLevel+pPartialTick/10);
		} else {
			pBlockEntity.lightLevel = Math.max(0,  pBlockEntity.lightLevel-pPartialTick/20);
		}

		pPoseStack.translate(.5f,3f/16f,1.3f/16f);
		pPoseStack.pushPose();
		pPoseStack.scale(1.1f,1.1f,0);
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				pBlockEntity.lightLevel,  pBlockEntity.lightLevel * .8f, 0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(3f/16f,7f/16f,0f);
		pPoseStack.scale(1.1f,1.1f,0);
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				pBlockEntity.lightLevel,  pBlockEntity.lightLevel * .8f, 0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(-3f/16f,7f/16f,0f);

		pPoseStack.scale(1.1f,1.1f,0);
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				pBlockEntity.lightLevel,  pBlockEntity.lightLevel * .8f, 0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();
	}
}
