package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityDwarfSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class BlockEntityRendererModernDwarfSignal extends BlockEntityRendererBase<BlockEntityDwarfSignal>{
	private static final float SCALE = 0.8f;

	public BlockEntityRendererModernDwarfSignal(BlockEntityRendererProvider.Context context) {
	}

	@Override
	protected ResourceLocation modelLoc() {
		return null; // return ResourceLocation.fromNamespace	AndPath(SwSignal.MOD_ID,"block/sw_4l_dwarf_signal_post_1970");
	}

	public double getLightHeightOffset() {
		return 0d;
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
		pPoseStack.translate(.5f * (1f - SCALE), 0f, 0f);
		pPoseStack.scale(SCALE, SCALE, SCALE);
		getRenderer().tesselateWithAO(
				pBlockEntity.getLevel(),
				this.getModel(pBlockEntity.getBlockState()),
				pBlockEntity.getBlockState(),
				pBlockEntity.getBlockPos(),
				pPoseStack,
				pBuffer.getBuffer(RenderType.solid()),
				true,
				pBlockEntity.getLevel().getRandom(),
				pPackedLight,
				pPackedOverlay
		);

		pBlockEntity.stepSignalLighting(pPartialTick, null, pBlockEntity.getCurrentDisplayingState(), false);

		float w = 0;

		pPoseStack.pushPose();
		pPoseStack.translate(.5f, getLightHeightOffset(), .5f);

		pPoseStack.pushPose();
		pPoseStack.translate(-3f / 16f, 5.5f / 16f, -3.65f / 16f);
		pPoseStack.scale(.6f, .6f, .6f);

		w = pBlockEntity.lightLevels[2];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w, w, w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);

		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(3f / 16f, 5.5f / 16f, -3.65f / 16f);
		pPoseStack.scale(.6f, .6f, .6f);

		w = pBlockEntity.lightLevels[3];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w, w, w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(3f / 16f, 11.5f / 16f, -3.65f / 16f);
		pPoseStack.scale(.6f, .6f, .6f);

		w = pBlockEntity.lightLevels[1];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w, w, w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();

		pPoseStack.pushPose();
		pPoseStack.translate(-1.5f / 16f, 10f / 16f, -3.65f / 16f);
		pPoseStack.scale(.6f, .6f, .6f);

		w = pBlockEntity.lightLevels[0];
		getRenderer().renderModel(
				pPoseStack.last(),
				pBuffer.getBuffer(RenderType.beaconBeam(BlockEntityRendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
				pBlockEntity.getBlockState(),
				BlockEntityRendererSignal.signalLightModel(),
				w, w, w,
				BlockEntityRendererSignal.FULLBRIGHT,
				pPackedOverlay
		);
		pPoseStack.popPose();


		pPoseStack.popPose();

		// pPoseStack.translate(.58f,.14f,1f-(2/16f));
//
		// pPoseStack.scale(.005f,.005f,.005f);
//
		// pPoseStack.rotateAround(new Quaternionf(new AxisAngle4f(
		// 		Mth.PI, 0, 0, 1
		// 		)
		// 		), .5f, .5f, .5f
		// );
//
//
//
		// Minecraft.getInstance().font.drawInBatch(
		// 		FormattedCharSequence.forward("Hb 193", Style.EMPTY),
		// 		0,0, 0xFFFFFF,
		// 		false,
		// 		pPoseStack.last().pose(),
		// 		pBuffer,
		// 		Font.DisplayMode.NORMAL,
		// 		0x000000,
		// 		pPackedOverlay
		// );
	}
}
