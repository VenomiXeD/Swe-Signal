package venomized.mods.extendedsignals.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Math;
import org.joml.Quaternionf;
import venomized.mods.extendedsignals.blockentity.ExtendedSignalsCoreBlockEntity;

@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererBase<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private BakedModel cachedModel;

    public BlockEntityRendererBase() {
    }

    protected static ModelBlockRenderer getRenderer() {
        return Minecraft.getInstance().getBlockRenderer().getModelRenderer();
    }
    public boolean isObjModel() {
        return false;
    }

    @Override
    public int getViewDistance() {
        return 1024;
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

    protected void renderSelfBlock(T pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        getRenderer().tesselateWithAO(
                pBlockEntity.getLevel(),
                getModel(pBlockEntity.getBlockState()),
                pBlockEntity.getBlockState(),
                pBlockEntity.getBlockPos(),
                pPoseStack,
                pBuffer.getBuffer(RenderType.solid()),
                true,
                pBlockEntity.getLevel().getRandom(),
                pPackedLight,
                pPackedOverlay
        );
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
        if (pBlockEntity instanceof ExtendedSignalsCoreBlockEntity esbe) {
            float angle = -esbe.getYOrientation();

            pPoseStack.rotateAround(
                    new Quaternionf(
                            new AxisAngle4f(Math.toRadians(angle), 0f, 1f, 0f)), .5f, 0, .5f
            );
        }
    }
}
