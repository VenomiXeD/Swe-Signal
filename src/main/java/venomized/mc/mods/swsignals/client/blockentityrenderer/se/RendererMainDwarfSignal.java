package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityDwarfSignal;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityMainDwarfSignal;

public class RendererMainDwarfSignal extends RendererSeDwarfSignal {

    public RendererMainDwarfSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public double getLightHeightOffset() {
        return 5d / 16d;
    }

    @Override
    public void render(BlockEntityDwarfSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockEntityMainDwarfSignal pMainBlockEntity = (BlockEntityMainDwarfSignal) pBlockEntity;

        super.render(pMainBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        float w = 0;
        pPoseStack.pushPose();
        pPoseStack.translate(3.15f / 16f, 17.6 / 16f, 5.25f / 16f);
        pPoseStack.scale(.85f, .85f, .85f);

        w = pMainBlockEntity.lightLevels[4];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pMainBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                w, 0, 0,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();

        w = pMainBlockEntity.lightLevels[5];
        pPoseStack.pushPose();
        pPoseStack.translate(11f / 16f, 5.1 / 16f, 5.275f / 16f);
        pPoseStack.scale(.8f, .8f, .8f);

        w = pMainBlockEntity.lightLevels[6];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pMainBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                0, w, 0,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();


        pPoseStack.pushPose();
        pPoseStack.translate(5f / 16f, 5f / 16f, 5.275f / 16f);
        pPoseStack.scale(.8f, .8f, .8f);

        w = pMainBlockEntity.lightLevels[5];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pMainBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                0, w, 0,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();
    }
}
