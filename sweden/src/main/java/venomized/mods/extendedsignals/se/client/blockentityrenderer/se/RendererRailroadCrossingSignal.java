package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalRendererHelper;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityRailroadCrossingSignal;

public class RendererRailroadCrossingSignal extends BlockEntityRendererBase<BlockEntityRailroadCrossingSignal> {
    public RendererRailroadCrossingSignal(BlockEntityRendererProvider.Context context) {
    }

    private boolean isObjModel() {
        return true;
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
    public void render(BlockEntityRailroadCrossingSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        renderer()
                .tesselateWithAO(
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

        pPoseStack.translate(.5f, 1 + 1.5 / 16f, 6f / 16f);

        pPoseStack.pushPose();
        pPoseStack.scale(1.1f, 1.1f, 0);
        renderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                ExtendedSignalsCoreModels.signalLightModel(),
                1, pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0, pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0,
                SignalRendererHelper.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();

        pPoseStack.translate(0, 0, 9.8f / 16f);

        pPoseStack.pushPose();
        pPoseStack.scale(1.1f, 1.1f, 0);
        renderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                ExtendedSignalsCoreModels.signalLightModel(),
                1, pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0, pBlockEntity.isRailroadCrossingControllerPowered() ? 1 : 0,
                SignalRendererHelper.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();
    }
}
