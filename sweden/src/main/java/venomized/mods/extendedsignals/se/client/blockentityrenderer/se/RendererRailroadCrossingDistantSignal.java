package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalRendererHelper;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityRailroadCrossingDistantSignal;

public class RendererRailroadCrossingDistantSignal extends BlockEntityRendererBase<BlockEntityRailroadCrossingDistantSignal> {
    public RendererRailroadCrossingDistantSignal(BlockEntityRendererProvider.Context context) {
        super(context);
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
    public void render(BlockEntityRailroadCrossingDistantSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        renderSelfBlock();

        if (pBlockEntity.blink() || pBlockEntity.isRailroadCrossingControllerPowered()) {
            pBlockEntity.lightLevel = Math.min(1, pBlockEntity.lightLevel + pPartialTick / 10);
        } else {
            pBlockEntity.lightLevel = Math.max(0, pBlockEntity.lightLevel - pPartialTick / 20);
        }

        pPoseStack.translate(.5f, 3f / 16f, 2f / 16f);
        pPoseStack.pushPose();
        pPoseStack.scale(1.2f, 1.2f, 0);
        renderer.renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                ExtendedSignalsCoreModels.signalLightModel(),
                pBlockEntity.lightLevel, pBlockEntity.lightLevel * .8f, 0,
                SignalRendererHelper.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(4f / 16f, 7f / 16f, 0f);
        pPoseStack.scale(1.2f, 1.2f, 0);
        renderer.renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                ExtendedSignalsCoreModels.signalLightModel(),
                pBlockEntity.lightLevel, pBlockEntity.lightLevel * .8f, 0,
                SignalRendererHelper.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(-4f / 16f, 7f / 16f, 0f);

        pPoseStack.scale(1.2f, 1.2f, 0);
        renderer.renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                ExtendedSignalsCoreModels.signalLightModel(),
                pBlockEntity.lightLevel, pBlockEntity.lightLevel * .8f, 0,
                SignalRendererHelper.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();
    }
}
