package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityHVCombinedZs3Signal;
import venomized.mods.extendedsignals.de.client.GermanyModels;
import venomized.mods.extendedsignals.de.signalling.CombinedSignalAspectCompositor;

public class RendererZs3CombinedSignal extends RendererSignal<BlockEntityHVCombinedZs3Signal> {
    public RendererZs3CombinedSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void renderAdditionalModels() {
        CachedBuffers.partial(GermanyModels.ZS3, blockEntity.getBlockState())
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));

        CachedBuffers.partial(GermanyModels.ZS3V, blockEntity.getBlockState())
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.cutoutMipped()));
    }

    /**
     * @param aspect
     */
    @Override
    public void renderAdditionalSignals(ISignalAspect aspect) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0, 0.5f);
        renderZs3((CombinedSignalAspectCompositor) aspect);
        renderZs3v((CombinedSignalAspectCompositor) aspect);
        poseStack.popPose();
    }

    public void renderZs3(CombinedSignalAspectCompositor signalAspect) {
        if (signalAspect.rawState().isStop())
            return;

        double kph = signalAspect.rawState().getMaxProceedSpeed();

        final int displayKph = Mth.clamp(
                Mth.floor(kph / 10), 1, 17
        );

        if (displayKph >= 17)
            return;

        final float[] uv = speedToUVCoordinates(displayKph);

        final Matrix4f pos = poseStack.last().pose();
        final Matrix3f normal = poseStack.last().normal();
        VertexConsumer zs3vertexConsumer = bufferSource.getBuffer(RenderType.beaconBeam(
                ExtendedSignalsGermany.res("textures/block/signals/de/numbers.png"), true
        ));
        zs3vertexConsumer
                .vertex(pos, -2.75f / 16f, 121.5f / 16f, -7.06f / 16f)
                .color(255, 255, 255, 255)
                .uv(uv[1], uv[3])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
        zs3vertexConsumer
                .vertex(pos, -2.75f / 16f, 128.75f / 16f, -7.06f / 16f)
                .color(255, 255, 255, 255)
                .uv(uv[1], uv[2])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
        zs3vertexConsumer
                .vertex(pos, 2.75f / 16f, 128.75f / 16f, -7.06f / 16f)
                .color(255, 255, 255, 255)
                .uv(uv[0], uv[2])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
        zs3vertexConsumer
                .vertex(pos, 2.75f / 16f, 121.5f / 16f, -7.06f / 16f)
                .color(255, 255, 255, 255)
                .uv(uv[0], uv[3])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
    }

    public boolean renderZs3v(CombinedSignalAspectCompositor signalAspect) {
        if (signalAspect.rawState().isStop())
            return false;
        if (signalAspect.rawState().getNextState() == null) {
            return false;
        }
        if (signalAspect.rawState().getNextState().isStop()) {
            return false;
        }
        if (signalAspect.rawState().getNextState().getMaxProceedSpeed() >= signalAspect.rawState().getMaxProceedSpeed()) {
            return false;
        }

        double kph = signalAspect.rawState().getNextState().getMaxProceedSpeed();

        final int displayKph = Mth.clamp(
                Mth.floor(kph / 10), 1, 17
        );

        if (displayKph >= 17)
            return false;

        final float[] uv = speedToUVCoordinates(displayKph);

        final Matrix4f pos = poseStack.last().pose();
        final Matrix3f normal = poseStack.last().normal();
        VertexConsumer zs3vertexConsumer = bufferSource.getBuffer(RenderType.beaconBeam(
                ExtendedSignalsGermany.res("textures/block/signals/de/numbers.png"), true
        ));
        zs3vertexConsumer
                .vertex(pos, -2.75f / 16f, 53.5f / 16f, -10.51f / 16f)
                .color(ISignalAspect.RGB.YELLOW.argb())
                .uv(uv[1], uv[3])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
        zs3vertexConsumer
                .vertex(pos, -2.75f / 16f, 60.75f / 16f, -10.51f / 16f)
                .color(ISignalAspect.RGB.YELLOW.argb())
                .uv(uv[1], uv[2])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
        zs3vertexConsumer
                .vertex(pos, 2.75f / 16f, 60.75f / 16f, -10.51f / 16f)
                .color(ISignalAspect.RGB.YELLOW.argb())
                .uv(uv[0], uv[2])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();
        zs3vertexConsumer
                .vertex(pos, 2.75f / 16f, 53.5f / 16f, -10.51f / 16f)
                .color(ISignalAspect.RGB.YELLOW.argb())
                .uv(uv[0], uv[3])
                .uv2(0xFFFFFF)
                .normal(normal, 0, 1, 0)
                .endVertex();

        return true;
    }

    private static float[] speedToUVCoordinates(int speed) {
        int index = speed - 1;
        int column = index % 4;
        int row = index / 4;
        int pxU0 = column * 13 + column + 1;
        int pxU1 = (column + 1) * 13 + column - 1;
        int pxV0 = row * 15 + row;
        int pxV1 = (row + 1) * 15 + row;

        return new float[]{
                (float) pxU0 / 64f,
                (float) pxU1 / 64f,
                (float) pxV0 / 64f,
                (float) pxV1 / 64f
        };
    }
}
