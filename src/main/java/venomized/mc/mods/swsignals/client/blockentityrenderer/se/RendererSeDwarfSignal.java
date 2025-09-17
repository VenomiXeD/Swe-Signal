    package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mc.mods.swsignals.blockentity.se.auxilliarysignals.BlockEntityDwarfSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public abstract class RendererSeDwarfSignal<T extends BlockEntityDwarfSignal> extends BlockEntityRendererBase<T> {
    private static final float SCALE = 1f;

    public RendererSeDwarfSignal(BlockEntityRendererProvider.Context context) {
    }

    public double getLightHeightOffset() {
        return 0d;
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
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        pPoseStack.translate(.5f * (1f - SCALE), 0f, 0f);
        pPoseStack.scale(SCALE, SCALE, SCALE);
        getRenderer().tesselateWithAO(
                pBlockEntity.getLevel(),
                this.getModel(pBlockEntity.getBlockState()),
                pBlockEntity.getBlockState(),
                pBlockEntity.getBlockPos(),
                pPoseStack,
                pBuffer.getBuffer(RenderType.solid()),
                true,
                pBlockEntity.getLevel().getRandom(),
                pPackedLight,
                pPackedOverlay
        );

        // pBlockEntity.clientTick(null, pBlockEntity.getCurrentDisplayingState(), false);

        float w = 0;

        pPoseStack.pushPose();
        pPoseStack.translate(.5f, getLightHeightOffset(), .5f);

        pPoseStack.pushPose();
        pPoseStack.translate(-3f / 16f, 5f / 16f, -2.725f / 16f);
        pPoseStack.scale(.8f, .8f, .8f);

        w = pBlockEntity.lightLevels[2];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                w, w, w,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );

        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(3f / 16f, 5f / 16f, -2.725f / 16f);
        pPoseStack.scale(.8f, .8f, .8f);

        w = pBlockEntity.lightLevels[3];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                w, w, w,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(3f / 16f, 11 / 16f, -2.725f / 16f);
        pPoseStack.scale(.8f, .8f, .8f);

        w = pBlockEntity.lightLevels[1];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                w, w, w,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.translate(-1.5f / 16f, 9.6f / 16f, -2.725f / 16f);
        pPoseStack.scale(.8f, .8f, .8f);

        w = pBlockEntity.lightLevels[0];
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                w, w, w,
                RendererSignal.FULLBRIGHT,
                pPackedOverlay
        );
        pPoseStack.popPose();


        pPoseStack.popPose();

        // pPoseStack.translate(.58f,.14f,1f-(2/16f));
//
        // pPoseStack.scale(.005f,.005f,.005f);
//
        // pPoseStack.rotateAround(new Quaternionf(new AxisAngle4f(
        // 		Mth.PI, 0, 0, 1
        // 		)
        // 		), .5f, .5f, .5f
        // );
//
//
//
        // Minecraft.getInstance().font.drawInBatch(
        // 		FormattedCharSequence.forward("Hb 193", Style.EMPTY),
        // 		0,0, 0xFFFFFF,
        // 		false,
        // 		pPoseStack.last().pose(),
        // 		pBuffer,
        // 		Font.DisplayMode.NORMAL,
        // 		0x000000,
        // 		pPackedOverlay
        // );
    }
}
