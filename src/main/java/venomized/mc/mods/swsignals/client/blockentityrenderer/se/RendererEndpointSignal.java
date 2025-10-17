package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityEndpointSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mc.mods.swsignals.rail.se.SwedishSignalAspect;

@OnlyIn(Dist.CLIENT)
public class RendererEndpointSignal extends BlockEntityRendererBase<BlockEntityEndpointSignal> {
    public RendererEndpointSignal(BlockEntityRendererProvider.Context context) {
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
    public void render(BlockEntityEndpointSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
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

        pPoseStack.translate(.5f, 1 / 16f, 8f / 16f);
        pPoseStack.translate(0f, 2.9f / 16f, -5.6f / 16f);

        pPoseStack.pushPose();
        pPoseStack.scale(1.1f, 1.1f, 0f);

        SwedishSignalAspect aspect = pBlockEntity.getCurrentDisplayingAspect();
        // pBlockEntity.clientTick(pPartialTick, aspect, pBlockEntity.getCurrentDisplayingState(), !pBlockEntity.valid());

        final float r = pBlockEntity.lightLevels[0];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                r, 0, 0,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();
    }
}
