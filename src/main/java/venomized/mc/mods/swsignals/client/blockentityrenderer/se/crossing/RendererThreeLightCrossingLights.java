package venomized.mc.mods.swsignals.client.blockentityrenderer.se.crossing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import org.joml.Math;
import venomized.mc.mods.swsignals.block.se.BlockSignal;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityThreeLightCrossingLights;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class RendererThreeLightCrossingLights extends BlockEntityRendererBase<BlockEntityThreeLightCrossingLights> {
    public RendererThreeLightCrossingLights(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BlockEntityThreeLightCrossingLights pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        if (pBlockEntity.getBlockState().getValue(BlockSignal.MOUNTED)) {
            pPoseStack.translate(0f,8f/16f,0f);
        }

        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        renderSelfBlock(pBlockEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        // Middle light when signal idle

        float w = 0;
        if (!pBlockEntity.isRailroadCrossingControllerPowered())
            w = Math.max(Mth.sin((((pBlockEntity.getLevel().getGameTime() + pPartialTick) % 40f) / 20f) * Mth.PI), 0f);
        renderLight(pBlockEntity, pPoseStack, pBuffer, pPackedOverlay, 0.5f, 2.9f / 16f, 2f / 16f, 1.2f, 1.2f, 0f, w, w, w);

        float period = 10f;

        float rr = 0;
        float rl = 0;
        if (pBlockEntity.isRailroadCrossingControllerPowered()) {
            rl = Mth.sin((((pBlockEntity.getLevel().getGameTime() + pPartialTick) % (period * 2f)) / period) * Mth.PI);
            rr = Mth.sin(((((pBlockEntity.getLevel().getGameTime()) + pPartialTick) % (period * 2f)) / period) * Mth.PI + Mth.PI);
        }

        renderLight(pBlockEntity, pPoseStack, pBuffer, pPackedOverlay, 8f / 16f - 4f / 16f, 10f / 16f, 2f / 16f, 1.2f, 1.2f, 0f, rl, 0, 0);
        renderLight(pBlockEntity, pPoseStack, pBuffer, pPackedOverlay, 8f / 16f + 4f / 16f, 10f / 16f, 2f / 16f, 1.2f, 1.2f, 0f, rr, 0, 0);
    }
}
