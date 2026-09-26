package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.core.util.SpriteUV;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class RendererGeneric<T extends BlockEntity> implements BlockEntityRenderer<T> {
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

    protected RendererGeneric() {
    }

    /**
     * @param blockEntity
     * @return
     */
    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return BlockEntityRenderer.super.getRenderBoundingBox(blockEntity).inflate(5);
    }

    @Override
    public int getViewDistance() {
        return 128;
    }

    public void renderLightAt(double x, double y, double z, float xscale, float yscale, float zscale, int r, int g, int b) {
        renderLightAtWithFlare(x, y, z, xscale, yscale, zscale, r, g, b, 0);
    }

    public void renderLightAtWithFlare(double x, double y, double z, float xscale, float yscale, float zscale, int r, int g, int b, int a) {
        renderFlare(x, y, z, r, g, b, a);
        poseStack.pushPose();
        poseStack.translate(
                x + 0.5d,
                y,
                z + 0.5d - 0.025d / 16d
        );

        poseStack.scale(
                xscale / 2f,
                yscale / 2f,
                zscale / 2f
        );
        CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
                .disableDiffuse()
                .light(0xFFFFFF)
                .color(r, g, b, 255)
                .renderInto(
                        poseStack,
                        bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)
                        )
                );
        poseStack.popPose();
    }

    protected void renderFlare(double x, double y, double z, int r, int g, int b, int a) {
        if (ExtendedSignalsConfig.CLIENT.flareEnabled.isFalse())
            return;
        Vector3f camForward = Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector();
        Vector3f signalForward = new Vector3f();
        poseStack.last().pose().transformDirection(0, 0, 1, signalForward);
        Vector3f directionSignalFromPlayer = blockEntity.getBlockPos().getCenter().toVector3f().sub(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().toVector3f()).normalize();

        double brightnessMultiplier = 1; //Mth.clampedMap(
        //        Minecraft.getInstance().cameraEntity.level().getMaxLocalRawBrightness(Minecraft.getInstance().cameraEntity.blockPosition()),
        //        ExtendedSignalsConfig.CLIENT.flareMinAmbientBrightness.getAsDouble(),
        //        ExtendedSignalsConfig.CLIENT.flareMaxAmbientBrightness.getAsDouble(),
        //        1d,
        //        0d
        //);
        double dist = Minecraft.getInstance().player.getEyePosition().distanceToSqr(blockEntity.getBlockPos().getX(), blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ());
        double brightnessDistance = Mth.clampedMap(
                dist, Mth.square(ExtendedSignalsConfig.CLIENT.flareMinDistance.getAsDouble()), Mth.square(ExtendedSignalsConfig.CLIENT.flareMaxDistance.getAsDouble()), 0d, 1d
        );

        float signalCameraAlignment = signalForward.dot(camForward);
        float signalCameraPosAlignmentForward = signalForward.dot(directionSignalFromPlayer);

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        renderUVMappedTexturedDisplayColored(
                new Vector3f(1f, 1f, -8f / 16f),
                new Vector3f(-1f, -1f, -8f / 16f),
                new SpriteUV(0, 0, 1, 1, ExtendedSignals.res("textures/block/flare.png")),
                true,
                r, g, b, (int) (a * Math.max(0, signalCameraAlignment) * brightnessDistance * brightnessMultiplier * Math.max(0, signalCameraPosAlignmentForward) * ExtendedSignalsConfig.CLIENT.flareAlphaMultiplier.getAsDouble())
        );
        poseStack.popPose();
    }

    protected void renderSelfBlock() {
        if (blockEntity instanceof IConfigurableModelBlockEntity configurableModelBlockEntity) {
            PartialModel model = configurableModelBlockEntity.variantData().getVariantModel();
            configurableModelBlockEntity.variantData().getAdditionalFeatures().forEach(additionalModel -> {
                if (additionalModel == null)
                    return;

                CachedBuffers.partial(additionalModel, blockEntity.getBlockState())
                        .light(packedLight)
                        .overlay(packedOverlay)
                        .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
            });

            if (model != null) {
                CachedBuffers.partial(model, blockEntity.getBlockState())
                        .light(packedLight)
                        .overlay(packedOverlay)
                        .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
                if (!configurableModelBlockEntity.variantData().isDisplayBlockModel())
                    return;
            }
        }
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
        renderSelfBlock();
    }

    protected void renderUVMappedTexturedDisplay(Vector3f topLeft, Vector3f bottomRight, SpriteUV spriteUV, boolean lit) {
        Vector3f bottomLeft = new Vector3f(topLeft.x(), bottomRight.y(), topLeft.z());
        Vector3f topRight = new Vector3f(bottomRight.x(), topLeft.y(), topLeft.z());
        Vector3f horizontal = new Vector3f(topRight).sub(topLeft);
        Vector3f vertical = new Vector3f(bottomLeft).sub(topLeft);
        Vector3f normal = horizontal.cross(vertical).normalize();

        VertexConsumer consumer = lit ?
                bufferSource.getBuffer(RenderType.beaconBeam(spriteUV.texture(), true)) :
                bufferSource.getBuffer(RenderType.entityCutoutNoCull(spriteUV.texture()));

        poseStack.pushPose();
        poseStack.translate(0.5f, 0, .5f);
        consumer.addVertex(poseStack.last(), topLeft.x(), topLeft.y(), topLeft.z())
                .setColor(255, 255, 255, 255)
                .setUv(spriteUV.u0(), spriteUV.v0())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(poseStack.last(), bottomLeft.x(), bottomLeft.y(), bottomLeft.z())
                .setColor(255, 255, 255, 255)
                .setUv(spriteUV.u0(), spriteUV.v1())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(poseStack.last(), bottomRight.x(), bottomRight.y(), bottomRight.z())
                .setColor(255, 255, 255, 255)
                .setUv(spriteUV.u1(), spriteUV.v1())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(poseStack.last(), topRight.x(), topRight.y(), topRight.z())
                .setColor(255, 255, 255, 255)
                .setUv(spriteUV.u1(), spriteUV.v0())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());

        poseStack.popPose();
    }

    protected void renderUVMappedTexturedDisplayColored(Vector3f topLeft, Vector3f bottomRight, SpriteUV spriteUV, boolean lit, int r, int g, int b, int a) {
        Vector3f bottomLeft = new Vector3f(topLeft.x(), bottomRight.y(), topLeft.z());
        Vector3f topRight = new Vector3f(bottomRight.x(), topLeft.y(), topLeft.z());
        Vector3f horizontal = new Vector3f(topRight).sub(topLeft);
        Vector3f vertical = new Vector3f(bottomLeft).sub(topLeft);
        Vector3f normal = horizontal.cross(vertical).normalize();

        VertexConsumer consumer = lit ?
                bufferSource.getBuffer(RenderType.beaconBeam(spriteUV.texture(), true)) :
                bufferSource.getBuffer(RenderType.entityCutoutNoCull(spriteUV.texture()));

        poseStack.pushPose();
        poseStack.translate(0.5f, 0, .5f);
        consumer.addVertex(poseStack.last(), topLeft.x(), topLeft.y(), topLeft.z())
                .setColor(r, g, b, a)
                .setUv(spriteUV.u0(), spriteUV.v0())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(poseStack.last(), bottomLeft.x(), bottomLeft.y(), bottomLeft.z())
                .setColor(r, g, b, a)
                .setUv(spriteUV.u0(), spriteUV.v1())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(poseStack.last(), bottomRight.x(), bottomRight.y(), bottomRight.z())
                .setColor(r, g, b, a)
                .setUv(spriteUV.u1(), spriteUV.v1())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());
        consumer.addVertex(poseStack.last(), topRight.x(), topRight.y(), topRight.z())
                .setColor(r, g, b, a)
                .setUv(spriteUV.u1(), spriteUV.v0())
                .setOverlay(packedOverlay)
                .setLight(lit ? 0xFFFFFFFF : packedLight)
                .setNormal(normal.x(), normal.y(), normal.z());

        poseStack.popPose();
    }

    protected void quickRenderPartialModel(PartialModel partialModel, float xOffset, float yOffset, float zOffset) {
        CachedBuffers.partial(partialModel, blockEntity.getBlockState())
                .translate(xOffset, yOffset, zOffset)
                .useLevelLight(blockEntity.getLevel())
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    protected void quickRenderPartialModel(PartialModel partialModel) {
        quickRenderPartialModel(partialModel, 0, 0, 0);
    }

    public static SpriteUV calculateSpriteUV(int idx, int textureW, int textureH, int cellW, int cellMarginX, ResourceLocation texture) {
        final int TEX_W = textureW;
        final int TEX_H = textureH;
//
        final int CELL_W = cellW;
        final int CELL_H = textureH;
//
        final int u0 = idx * (CELL_W + cellMarginX);
        final int u1 = u0 + CELL_W;
        final int v0 = 0;
        final int v1 = v0 + CELL_H;
//
        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H, texture);
    }
}
