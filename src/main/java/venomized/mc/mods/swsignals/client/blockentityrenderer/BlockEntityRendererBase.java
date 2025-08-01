package venomized.mc.mods.swsignals.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.Sw45DegreeBlock;
import venomized.mc.mods.swsignals.block.se.BlockModernTwoLightSignal;

@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererBase<T extends BlockEntity> implements BlockEntityRenderer<T> {
	private BakedModel cachedModel;

	public BlockEntityRendererBase() {
	}

	protected ResourceLocation modLoc(String p) {
		return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, p);
	}

	@Override
	public int getViewDistance() {
		return 1024;
	}

	protected ModelBlockRenderer getRenderer() {
		return Minecraft.getInstance().getBlockRenderer().getModelRenderer();
	}

	protected ResourceLocation modelLoc() {
		return null;
	}

	protected BakedModel getModel(BlockState currentBlockState) {
		if (cachedModel == null) {
			ResourceLocation modelLoc = modelLoc();
			if (modelLoc == null) {
				cachedModel = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(currentBlockState);
			} else {
				Minecraft mc = Minecraft.getInstance();
				cachedModel = mc.getModelManager().getModel(modelLoc);
			}
		}
		return cachedModel;
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
		if (pBlockEntity.getBlockState().hasProperty(Sw45DegreeBlock.ORIENTATION)) {
			pPoseStack.rotateAround(
					new Quaternionf(
							new AxisAngle4f(pBlockEntity.getBlockState().getValue(BlockModernTwoLightSignal.ORIENTATION) / -4f * Mth.PI, 0, 1, 0)), .5f, 0, .5f
			);
		}
	}
}
