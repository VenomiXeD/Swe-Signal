package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntityThreeLightDistantSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mc.mods.swsignals.rail.se.SwedishSignalAspect;

public class RendererThreeLightDistantSignal extends BlockEntityRendererBase<BlockEntityThreeLightDistantSignal> {
    public RendererThreeLightDistantSignal(BlockEntityRendererProvider.Context context) {
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
    public void render(BlockEntityThreeLightDistantSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
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

        // Center light in proper position
        pPoseStack.translate(.5f, 1 / 16f, 8f / 16f);
        pPoseStack.translate(0f, 1.9f / 16f, -5.6f / 16f);

        SwedishSignalAspect aspect = pBlockEntity.getCurrentDisplayingAspect();
        // pBlockEntity.clientTick(aspect, pBlockEntity.getCurrentDisplayingState(), !pBlockEntity.valid() || aspect == null);

        pPoseStack.translate(0, 6.5 / 16f * 2, 0);
        for (int i = 0; i < 3; i++) {

            float r = 0;
            float g = 0;
            float b = 0;
            if (i == 1) {
                float w = pBlockEntity.lightLevels[i + 2];
                r = w;
                g = w;
                b = w;
            } else {
                g = pBlockEntity.lightLevels[i + 2];
            }
            pPoseStack.pushPose();
            pPoseStack.scale(1.1f, 1.1f, 0f);
            getRenderer().renderModel(
                    pPoseStack.last(),
                    pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                    pBlockEntity.getBlockState(),
                    RendererSignal.signalLightModel(),
                    r, g, b,
                    RendererSignal.FULLBRIGHT,
                    pPackedOverlay
            );
            pPoseStack.popPose();
            pPoseStack.translate(0, -6.5 / 16f, 0);
        }
    }
}
