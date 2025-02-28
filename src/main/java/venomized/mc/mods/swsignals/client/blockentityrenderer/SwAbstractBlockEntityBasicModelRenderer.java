package venomized.mc.mods.swsignals.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.block.BlockModernTwoLightSignal;
import venomized.mc.mods.swsignals.block.SwAbstract45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.BlockEntityUSign;

@OnlyIn(Dist.CLIENT)
public abstract class SwAbstractBlockEntityBasicModelRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

	private BakedModel model;
	protected abstract ResourceLocation modelLoc();

	protected BakedModel getModel() {
		if (model == null) {
			Minecraft mc = Minecraft.getInstance();
			model = mc.getModelManager().getModel(modelLoc());
		}
		return model;
	}

	public SwAbstractBlockEntityBasicModelRenderer() {

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
	public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		if(pBlockEntity.getBlockState().hasProperty(SwAbstract45DegreeBlock.ORIENTATION)) {
			pPoseStack.rotateAround(
					new Quaternionf(
							new AxisAngle4f(pBlockEntity.getBlockState().getValue(BlockModernTwoLightSignal.ORIENTATION)/-4f*Mth.PI,0,1,0)), .5f,0,.5f
			);
		}
		// Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithAO(pBlockEntity.getLevel(), getModel(), pBlockEntity.getBlockState(), pBlockEntity.getBlockPos(), pPoseStack, pBuffer.getBuffer(RenderType.solid()), true, pBlockEntity.getLevel().getRandom(), pPackedLight, pPackedOverlay);
	}
}
