package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
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
    protected T blockEntity;

    protected float partialTick;
    protected int packedLight;
    protected int packedOverlay;
    protected MultiBufferSource bufferSource;
    protected PoseStack poseStack;
    protected ModelBlockRenderer renderer;

    public BlockEntityRendererBase(BlockEntityRendererProvider.Context context) {
        renderer = context.getBlockRenderDispatcher().getModelRenderer();
    }
    @Override
    public int getViewDistance() {
        return 1024;
    }

    private BakedModel cachedModel;
    protected BakedModel getModel(BlockState currentBlockState) {
        if (cachedModel == null) {
            cachedModel = Minecraft.getInstance().getBlockRenderer()
                    .getBlockModel(currentBlockState);
        }
        return cachedModel;
    }

    protected void renderSelfBlock() {

        CachedBuffers.block(CachedBuffers.GENERIC_BLOCK, blockEntity.getBlockState())
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
        // renderer.tesselateBlock(
        //         pBlockEntity.getLevel(),
        //         getModel(pBlockEntity.getBlockState()),
        //         pBlockEntity.getBlockState(),
        //         pBlockEntity.getBlockPos(),
        //         pPoseStack,
        //         bufferSource.getBuffer(RenderType.cutoutMipped()),
        //         false,
        //         pBlockEntity.getLevel().getRandom(),
        //         packedLight,
        //         packedOverlay, ModelData.EMPTY, RenderType.cutoutMipped()
        // );
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
        blockEntity = pBlockEntity;
        poseStack = pPoseStack;
        bufferSource = pBuffer;
        packedLight = pPackedLight;
        packedOverlay = pPackedOverlay;
        partialTick = pPartialTick;

        if (blockEntity instanceof IOrientedBlockEntity orientableBlock) {
            float angle = -orientableBlock.getYOrientation();

            pPoseStack.rotateAround(
                    new Quaternionf(
                            new AxisAngle4f(Math.toRadians(angle), 0f, 1f, 0f)), .5f, 0, .5f
            );
        }
    }


}
