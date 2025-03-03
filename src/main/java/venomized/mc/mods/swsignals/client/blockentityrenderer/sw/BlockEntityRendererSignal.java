package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.sw.BlockAbstractSignal;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntitySignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererSignal<T extends BlockEntitySignal> extends BlockEntityRendererBase<T> {
	protected static final int FULLBRIGHT = 0xFFFFFF;
	public static ResourceLocation SIGNAL_LIGHT_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, "block/light");
	public static ResourceLocation SIGNAL_LIGHT_TEX_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, "textures/block/light.png");
	private static BakedModel SIGNAL_LIGHT_MODEL;

	public static BakedModel signalLightModel() {
		if (SIGNAL_LIGHT_MODEL == null) {
			SIGNAL_LIGHT_MODEL = Minecraft.getInstance().getModelManager().getModel(SIGNAL_LIGHT_MODEL_LOC);
		}
		return SIGNAL_LIGHT_MODEL;
	}

	@Override
	public int getViewDistance() {
		return 1024;
	}

	@Override
	protected BakedModel getModel() {
		return this.getSignalModel();
	}

	@Override
	protected ResourceLocation modelLoc() {
		return this.getSignalModelLoc();
	}

	public BakedModel getSignalModel() {
		ResourceLocation signalModelLoc = this.getSignalModelLoc();
		if (signalModelLoc == null) {
			throw new IllegalStateException("BlockEntityRender may not have a null model location");
		}
		return Minecraft.getInstance().getModelManager().getModel(this.getSignalModelLoc());
	}

	public abstract ResourceLocation getSignalModelLoc();

	public boolean isObjModel() {
		return false;
	}

	@Override
	public void render(T t, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		super.render(t, partialTick, poseStack, multiBufferSource, light, overlay);

		int lightCount = t.getLightCount();

		ModelBlockRenderer renderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();

		//poseStack.pushPose();

		// poseStack.rotateAround(
		// 		new Quaternionf(
		// 				new AxisAngle4f(t.getBlockState().getValue(BlockModernTwoLightSignal.ORIENTATION)/-4f*Mth.PI,0,1,0)), .5f,0,.5f
		// );

		if (t.getBlockState().getValue(BlockAbstractSignal.MOUNTED)) {
			poseStack.translate(0, 8 / 16d, 0);
		}

		if (this.isObjModel()) {
			poseStack.pushPose();
			poseStack.translate(.5, 0, .5);
		}
		renderer.tesselateWithAO(t.getLevel(), this.getModel(), t.getBlockState(), t.getBlockPos(), poseStack, multiBufferSource.getBuffer(RenderType.solid()), true, t.getLevel().getRandom(), light, overlay);
		if (this.isObjModel()) {
			poseStack.popPose();
		}

		poseStack.translate(.5d, 0d, .5d);
		poseStack.translate(0, 2.9 / 16d, -5.6d / 16d);
// SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;//
		SwedishSignalAspect aspect = t.getCurrentDisplayingAspect();
		t.stepSignalLighting(partialTick, aspect, t.getCurrentDisplayingState(), !t.valid() || aspect == null);

		poseStack.translate(0, 0.5d * (lightCount - 1), 0);

		for (int i = 0; i < lightCount; i++) {
			float r = 0;
			float g = 0;
			float b = 0;

			switch (i) {
				// Second light is red only
				case 1:
					r = t.lightLevels[i];
					break;
				// Fourth light is white only
				case 3:
					r = t.lightLevels[i];
					g = t.lightLevels[i];
					b = t.lightLevels[i];
				default:
					g = t.lightLevels[i];
					break;
			}

			poseStack.pushPose();
			// Rescale it to fit properly in the spots
			poseStack.scale(1.1f, 1.1f, 0f);
			renderer.renderModel(
					poseStack.last(),
					// RenderType.debugFilledBox()
					// multiBufferSource.getBuffer(RenderType.debugQuads()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
					// multiBufferSource.getBuffer(RenderType.eyes(ResourceLocation.fromNamespaceAndPath("swsignal","textures/block/light.png"))), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
					multiBufferSource.getBuffer(RenderType.beaconBeam(SIGNAL_LIGHT_TEX_LOC, true)), t.getBlockState(), signalLightModel(), r, g, b, FULLBRIGHT, overlay
					// multiBufferSource.getBuffer(RenderType.()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
			);
			poseStack.popPose();

			poseStack.translate(0, -8 / 16d, 0);
		}
		//poseStack.popPose();
	}


}
