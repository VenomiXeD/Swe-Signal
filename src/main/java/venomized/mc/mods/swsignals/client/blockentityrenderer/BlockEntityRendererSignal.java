package venomized.mc.mods.swsignals.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.SwedishSignalAspect;
import venomized.mc.mods.swsignals.block.BlockAbstractSignal;
import venomized.mc.mods.swsignals.block.BlockModernTwoLightSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntitySignalBlock;

@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererSignal<T extends BlockEntitySignalBlock> extends SwAbstractBlockEntityBasicModelRenderer<T> {
	protected static final int FULLBRIGHT = 0xFFFFFF;

	private static BakedModel SIGNAL_LIGHT_MODEL;
	public static ResourceLocation SIGNAL_LIGHT_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/light");

	public BakedModel signalLightModel() {
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
	public boolean isObjModel() {return false;}

	@Override
	public void render(T t, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		super.render(t,partialTick,poseStack,multiBufferSource, light, overlay);

		int lightCount = t.getLightCount();

		ModelBlockRenderer renderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();

		//poseStack.pushPose();

		// poseStack.rotateAround(
		// 		new Quaternionf(
		// 				new AxisAngle4f(t.getBlockState().getValue(BlockModernTwoLightSignal.ORIENTATION)/-4f*Mth.PI,0,1,0)), .5f,0,.5f
		// );

		if (t.getBlockState().getValue(BlockAbstractSignal.MOUNTED)) {
			poseStack.translate(0,8/16d,0);
		}

		if (this.isObjModel()) {
			poseStack.pushPose();
			poseStack.translate(.5, 0, .5);
		}
 		renderer.tesselateWithAO(t.getLevel(), this.getModel(), t.getBlockState(), t.getBlockPos(), poseStack, multiBufferSource.getBuffer(RenderType.solid()), true, t.getLevel().getRandom(), light, overlay);
		if (this.isObjModel()) {
			poseStack.popPose();
		}

		poseStack.translate(.5d,0d,.5d);
		poseStack.translate(0,2.9/16d,-5.6d/16d);
// SwedishSignalAspect.PROCEED_80_EXPECT_PROCEED_40;//
		SwedishSignalAspect aspect = t.getCurrentAspect();

		if (!t.valid() || aspect == null) {
			for (int i = 0; i < t.getLightCount(); i++) {
				t.lightLevels[i] = t.blink() ? 1 : 0;
			}
		}
		else {
			for(int i = 0;i<lightCount;i++) {
				char s = aspect.getLightPattern().charAt(i);
				switch(s) {
					case 'S':
						t.lightLevels[i] = Math.min(1,t.lightLevels[i]+(partialTick/20));
						break;
					case 'F':
						if (t.blink()) {
							t.lightLevels[i] = Math.min(1,t.lightLevels[i]+(partialTick/20));
						}
						else {
							t.lightLevels[i] = Math.max(0,t.lightLevels[i]-(partialTick/40));
						}
						break;
					default:
						t.lightLevels[i] = Math.max(0, t.lightLevels[i]-(partialTick/20));
				}
			}
		}

		poseStack.translate(0,0.5d*(lightCount-1),0);

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
			poseStack.scale(1.1f,1.1f,1.1f);
			renderer.renderModel(
					poseStack.last(),
					// RenderType.debugFilledBox()
					// multiBufferSource.getBuffer(RenderType.debugQuads()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
					// multiBufferSource.getBuffer(RenderType.eyes(ResourceLocation.fromNamespaceAndPath("swsignal","textures/block/light.png"))), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
					multiBufferSource.getBuffer(RenderType.beaconBeam(ResourceLocation.fromNamespaceAndPath("swsignal","textures/block/light.png"),true)), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
					// multiBufferSource.getBuffer(RenderType.()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
			);
			poseStack.popPose();

			poseStack.translate(0, -8 / 16d, 0);
		}
		//poseStack.popPose();
	}


}
