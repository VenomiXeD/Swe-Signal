package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public abstract class RendererGeneric<T extends BlockEntity> implements BlockEntityRenderer<T> {
    protected T blockEntity;

    protected float partialTick;
    protected int packedLight;
    protected int packedOverlay;
    protected MultiBufferSource bufferSource;
    protected PoseStack poseStack;
    protected ModelBlockRenderer renderer;

    public RendererGeneric(BlockEntityRendererProvider.Context context) {
        renderer = context.getBlockRenderDispatcher().getModelRenderer();
    }

    @Override
    public int getViewDistance() {
        return 1024;
    }

    public void renderLightAt(double x, double y, double z, float xscale, float yscale, float zscale, int r, int g, int b) {
        poseStack.pushPose();
        poseStack.translate(
                x + 0.5d,
                y,
                z + 0.5d
        );

        poseStack.scale(
                xscale / 2f,
                yscale / 2f,
                zscale / 2f
        );
        CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
                .color(r, g, b, 255)
                .overlay(packedOverlay)
                .disableDiffuse()
                .light(0xFFFFFF)
                .renderInto(
                        poseStack,
                        bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)
                        )
                );
        poseStack.popPose();
    }

    protected void renderSelfBlock() {
        CachedBuffers.block(CachedBuffers.GENERIC_BLOCK, blockEntity.getBlockState())
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
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

        if (blockEntity instanceof IConfigurableModelBlockEntity configurableModel) {
            poseStack.translate(0.5f, 0.5f, 0.5f);
            poseStack.translate(
                    configurableModel.getXGblOffset(),
                    configurableModel.getYGblOffset(),
                    configurableModel.getZGblOffset()
            );
            pPoseStack.mulPose(
                    new Quaternionf()
                            .rotateXYZ(
                                    Mth.DEG_TO_RAD * configurableModel.getXOrientation(),
                                    Mth.DEG_TO_RAD * -configurableModel.getYOrientation(),
                                    Mth.DEG_TO_RAD * configurableModel.getZOrientation()
                            )
            );
            poseStack.translate(-0.5f, -0.5f, -0.5f);
            poseStack.translate(
                    configurableModel.getXLocOffset(),
                    configurableModel.getYLocOffset(),
                    configurableModel.getZLocOffset()
            );
        }

        doRender();
    }

    public void doRender() {
    }
}
