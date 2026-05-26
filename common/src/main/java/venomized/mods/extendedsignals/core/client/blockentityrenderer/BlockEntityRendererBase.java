package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.AxisAngle4f;
import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import venomized.mods.extendedsignals.core.blockentity.IOrientedBlockEntity;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererBase<T extends BlockEntity> implements BlockEntityRenderer<T> {
    protected final Vector3d baseModelOffset;
    protected int packedLight;
    protected int packedOverlay;
    protected MultiBufferSource bufferSource;

    private BakedModel cachedModel;

    public BlockEntityRendererBase(BlockEntityRendererProvider.Context context) {
        this();
    }

    public BlockEntityRendererBase() {
        baseModelOffset = configureModelOffset();
    }

    protected static ModelBlockRenderer renderer() {
        return Minecraft.getInstance().getBlockRenderer().getModelRenderer();
    }

    protected Vector3d configureModelOffset() {
        return new Vector3d(0d, 0d, 0d);
    }

    @Override
    public int getViewDistance() {
        return 1024;
    }

    protected BakedModel getModel(BlockState currentBlockState) {
        if (cachedModel == null) {
            cachedModel = Minecraft.getInstance().getBlockRenderer()
                    .getBlockModel(currentBlockState);
        }
        return cachedModel;
    }

    protected void renderSelfBlock(T pBlockEntity, PoseStack pPoseStack) {
        pPoseStack.pushPose();
        pPoseStack.translate(
                baseModelOffset.x,
                baseModelOffset.y,
                baseModelOffset.z
        );
        renderer().tesselateWithAO(
                pBlockEntity.getLevel(),
                getModel(pBlockEntity.getBlockState()),
                pBlockEntity.getBlockState(),
                pBlockEntity.getBlockPos(),
                pPoseStack,
                bufferSource.getBuffer(RenderType.cutoutMipped()),
                false,
                pBlockEntity.getLevel().getRandom(),
                packedLight,
                packedOverlay, ModelData.EMPTY, RenderType.cutoutMipped()
        );

        pPoseStack.popPose();
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
        bufferSource = pBuffer;
        packedLight = pPackedLight;
        packedOverlay = pPackedOverlay;

        if (pBlockEntity instanceof IOrientedBlockEntity orientableBlock) {
            float angle = -orientableBlock.getYOrientation();

            pPoseStack.rotateAround(
                    new Quaternionf(
                            new AxisAngle4f(Math.toRadians(angle), 0f, 1f, 0f)), .5f, 0, .5f
            );
        }
    }


}
