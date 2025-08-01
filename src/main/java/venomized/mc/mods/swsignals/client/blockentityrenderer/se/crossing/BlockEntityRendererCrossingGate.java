package venomized.mc.mods.swsignals.client.blockentityrenderer.se.crossing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityCrossingGate;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class BlockEntityRendererCrossingGate extends BlockEntityRendererBase<BlockEntityCrossingGate> {
	public static String ARM_5 = "block/signals/se/crossing/gate_5";

	private final BakedModel MODEL_ARM_5 = Minecraft.getInstance().getModelManager().getModel(SwSignal.modLoc(ARM_5));

	public BlockEntityRendererCrossingGate(BlockEntityRendererProvider.Context context) {
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
				pBlockEntity.rotation() / 20f * Mth.PI / 60f, 1, 0, 0)
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
				MODEL_ARM_5,
				pBlockEntity.getBlockState(),
				pBlockEntity.getBlockPos(),
				pPoseStack,
				pBuffer.getBuffer(RenderType.solid()),
				false,
				pBlockEntity.getLevel().getRandom(),
				pPackedLight,
				pPackedOverlay
		);

		pBlockEntity.rotationDelta(pPartialTick);
	}
}
