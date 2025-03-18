package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityDwarfSignal;

public class BlockEntityRendererModernMainDwarfSignal extends BlockEntityRendererModernDwarfSignal{
	@Override
	public double getLightHeightOffset() {
		return 5d/16d;
	}

	/**
	 * @param pBlockEntity
	 * @param pPartialTick
	 * @param pPoseStack
	 * @param pBuffer
	 * @param pPackedLight
	 * @param pPackedOverlay
	 */
	@Override
	public void render(BlockEntityDwarfSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

		float w = 0;
		pPoseStack.pushPose();
		pPoseStack.translate(11f/16f,21/16f,4.3/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[4];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w,0,0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		w = pBlockEntity.lightLevels[5];
		pPoseStack.pushPose();
		pPoseStack.translate(11f/16f,5.1/16f,4.3/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[6];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				0,w,0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();



		pPoseStack.pushPose();
		pPoseStack.translate(5f/16f,5.1/16f,4.3/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[5];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				0,w,0,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();
	}
}
