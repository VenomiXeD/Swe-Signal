package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityDwarfSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class BlockEntityRendererModernDwarfSignal extends BlockEntityRendererBase<BlockEntityDwarfSignal> {
	private static float SCALE = 0.8f;

	@Override
	protected ResourceLocation modelLoc() {
		return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/sw_4l_dwarf_signal_post_1970");
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
		pPoseStack.translate(.5f*(1f-SCALE),0f,0f);
		pPoseStack.scale(SCALE,SCALE,SCALE);
		getRenderer().tesselateWithAO(
				pBlockEntity.getLevel(),
				this.getModel(),
				pBlockEntity.getBlockState(),
				pBlockEntity.getBlockPos(),
				pPoseStack,
				pBuffer.getBuffer(RenderType.solid()),
				true,
				pBlockEntity.getLevel().getRandom(),
				pPackedLight,
				pPackedOverlay
		);

		pBlockEntity.stepSignalLighting(pPartialTick,null, pBlockEntity.getCurrentDisplayingState(), false);

		float w = 0;

		pPoseStack.pushPose();
		pPoseStack.translate(.5f,0,.5f);

		pPoseStack.pushPose();
		pPoseStack.translate(-3f/16f,5.5f/16f,-3.65f/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[2];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w,w,w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);

		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(3f/16f,5.5f/16f,-3.65f/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[3];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w,w,w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(3f/16f,11.5f/16f,-3.65f/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[1];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w,w,w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(-1.5f/16f,10f/16f,-3.65f/16f);
		pPoseStack.scale(.6f,.6f,.6f);

		w = pBlockEntity.lightLevels[0];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC,true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w,w,w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();


		pPoseStack.popPose();
	}
}
