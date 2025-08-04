package venomized.mc.mods.swsignals.client.blockentityrenderer.se.crossing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.block.se.SeModels;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityCrossingGate;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mc.mods.swsignals.util.MathHelp;

@OnlyIn(Dist.CLIENT)
public class RendererCrossingGate extends BlockEntityRendererBase<BlockEntityCrossingGate> {
	private static final float ARM_MOVEMENT_TIME = 45f;
	// public static String ARM_5 = ;

	// private final BakedModel MODEL_ARM_5 = Minecraft.getInstance().getModelManager().getModel(SwSignal.modLoc(ARM_5));
//
	public RendererCrossingGate(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(BlockEntityCrossingGate pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

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

		pPoseStack.translate(8f / 16f, 1.0f, 8f / 16f);

		pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(
				MathHelp.easeInOutBack(pBlockEntity.progress(),.5f) * Mth.HALF_PI, 1, 0, 0)
		));

		// getRenderer().renderModel(
		// 		pPoseStack.last(),
		// 		pBuffer.getBuffer(RenderType.solid()),
		// 		pBlockEntity.getBlockState(),
		// 		MODEL_ARM_5,
		// 		1,
		// 		1,
		// 		1,
		// 		pPackedLight,
		// 		pPackedOverlay
		// );

		getRenderer().tesselateBlock(
				pBlockEntity.getLevel(),
				SeModels.ARM_5.get(),
				pBlockEntity.getBlockState(),
				pBlockEntity.getBlockPos(),
				pPoseStack,
				pBuffer.getBuffer(RenderType.solid()),
				false,
				pBlockEntity.getLevel().getRandom(),
				pPackedLight,
				pPackedOverlay
		);

		pBlockEntity.progressDelta(pPartialTick/20f/ARM_MOVEMENT_TIME);
	}

	@Override
	public boolean shouldRenderOffScreen(BlockEntityCrossingGate pBlockEntity) {
		return true;
	}


}
