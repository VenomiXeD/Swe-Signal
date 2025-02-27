package venomized.mc.mods.swsignals.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.block.BlockTwoLightSignal;
import venomized.mc.mods.swsignals.block.SwAbstract45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntityBasicModel;

@OnlyIn(Dist.CLIENT)
public class BlockEntityBasicModelRenderer implements BlockEntityRenderer<SwBlockEntityBasicModel> {

	public BlockEntityBasicModelRenderer() {
		Minecraft mc = Minecraft.getInstance();
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
	public void render(SwBlockEntityBasicModel pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		if(pBlockEntity.getBlockState().hasProperty(SwAbstract45DegreeBlock.ORIENTATION)) {

			pPoseStack.pushPose();
			pPoseStack.rotateAround(
					new Quaternionf(
							new AxisAngle4f(pBlockEntity.getBlockState().getValue(BlockTwoLightSignal.ORIENTATION)/-4f*Mth.PI,0,1,0)), .5f,0,.5f
			);

			Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithAO(pBlockEntity.getLevel(), pBlockEntity., pBlockEntity.getBlockState(), pBlockEntity.getBlockPos(), pPoseStack, pBuffer.getBuffer(RenderType.solid()), true, pBlockEntity.getLevel().getRandom(), pPackedLight, pPackedOverlay);


			pPoseStack.popPose();
		}
	}
}
