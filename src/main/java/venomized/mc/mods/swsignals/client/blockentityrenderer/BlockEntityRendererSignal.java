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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.SwedishSignalAspect;
import venomized.mc.mods.swsignals.block.BlockAbstractSignal;
import venomized.mc.mods.swsignals.block.BlockTwoLightSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntitySignalBlock;

@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererAbstractSignal<T extends BlockEntitySignalBlock> implements BlockEntityRenderer<T> {
	protected static final int FULLBRIGHT = 0xFFFFFF;

	private static BakedModel SIGNAL_LIGHT_MODEL;
	public static ResourceLocation SIGNAL_LIGHT_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/light");
	public BakedModel signalLightModel() {
		if (SIGNAL_LIGHT_MODEL == null) {
			SIGNAL_LIGHT_MODEL = Minecraft.getInstance().getModelManager().getModel(SIGNAL_LIGHT_MODEL_LOC);
		}
		return SIGNAL_LIGHT_MODEL;
	}

	private BakedModel SIGNAL_MODEL;
	private BakedModel signalModel() {
		if (SIGNAL_MODEL == null) {
			SIGNAL_MODEL = Minecraft.getInstance().getModelManager().getModel(this.getSignalModelLocation());
		}
		return SIGNAL_MODEL;
	}
	protected abstract ResourceLocation getSignalModelLocation();

	@Override
	public void render(T t, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		int lightCount = t.getLightCount();

		ModelBlockRenderer renderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();

		poseStack.pushPose();

		poseStack.rotateAround(
				new Quaternionf(
						new AxisAngle4f(t.getBlockState().getValue(BlockTwoLightSignal.ORIENTATION)/-4f*Mth.PI,0,1,0)), .5f,0,.5f
		);

		if (t.getBlockState().getValue(BlockAbstractSignal.MOUNTED)) {
			poseStack.translate(0,8/16d,0);
		}

 		renderer.tesselateWithAO(t.getLevel(), signalModel(), t.getBlockState(), t.getBlockPos(), poseStack, multiBufferSource.getBuffer(RenderType.solid()), true, t.getLevel().getRandom(), light, overlay);


		poseStack.translate(.5d,0d,.5d);
		poseStack.translate(0,5/16d,-4d/16d);
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

			renderer.renderModel(
					poseStack.last(),
					multiBufferSource.getBuffer(RenderType.solid()), null, this.signalLightModel(), r, g, b, FULLBRIGHT, overlay
			);

			poseStack.translate(0, -8 / 16d, 0);
		}
		poseStack.popPose();
	}


}
