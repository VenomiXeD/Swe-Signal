package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.client.blockentityrenderer.SignalRendererHelper;
import venomized.mods.extendedsignals.se.SwedishSignalAspect;
import venomized.mods.extendedsignals.se.blockentity.mainsignals.BlockEntitySignal;

import static venomized.mods.extendedsignals.client.blockentityrenderer.SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC;

@OnlyIn(Dist.CLIENT)
public class RendererSignal<T extends BlockEntitySignal>
        extends BlockEntityRendererBase<T> {


    public RendererSignal(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public boolean shouldRenderOffScreen(T pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(T pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    protected ResourceLocation modelLoc() {
        return null;
    }

    public boolean isObjModel() {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(T t, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light,
                       int overlay) {
        super.render(t, partialTick, poseStack, multiBufferSource, light, overlay);

        int lightCount = t.getLightCount();

        ModelBlockRenderer renderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();

        // What a garbage way to center something
        if (this.isObjModel()) {
            poseStack.pushPose();
            poseStack.translate(.5, 0, .5);
        }

        poseStack.translate(0,0,9f/16f);
        renderer.tesselateWithAO(
                t.getLevel(),
                this.getModel(t.getBlockState()),
                t.getBlockState(),
                t.getBlockPos(),
                poseStack,
                multiBufferSource.getBuffer(
                        RenderType.solid()),
                true,
                t.getLevel().getRandom(),
                light,
                overlay
        );
        // poseStack.translate(0,0,-9f/16f);

        if (this.isObjModel()) {
            poseStack.popPose();
        }

        poseStack.translate(.5d, 3.1d/16d, 16.7d/16d);
        poseStack.translate(0, 2.75f / 16d, -5.6d / 16d);

        SwedishSignalAspect aspect = t.getCurrentDisplayingAspect();
        // t.clientTick(partialTick, aspect, t.getCurrentDisplayingState(), !t.valid() || aspect == null);

        poseStack.translate(0, 0.5d * (lightCount - 1), 0);

        poseStack.translate(0f, 10.35 / 16f, -3.001f / 16f);


        for (int i = 0; i < lightCount; i++) {
            float r = 0;
            float g = 0;
            float b = 0;

            switch (i) {
                // Second light is red only
                case 1:
                    r = Mth.clamp(t.lightLevels[i] + ((t.blink() && t.lightLevels[i] != 0 ? partialTick : -partialTick) / 20f), 0f, 1f);
                    break;
                // Fourth light is white only
                case 3:
                    r = Mth.clamp(t.lightLevels[i] + ((t.blink() && t.lightLevels[i] != 0 ? partialTick : -partialTick) / 20f), 0f, 1f);
                    g = Mth.clamp(t.lightLevels[i] + ((t.blink() && t.lightLevels[i] != 0 ? partialTick : -partialTick) / 20f), 0f, 1f);
                    b = Mth.clamp(t.lightLevels[i] + ((t.blink() && t.lightLevels[i] != 0 ? partialTick : -partialTick) / 20f), 0f, 1f);
                default:
                    g = Mth.clamp(t.lightLevels[i] + ((t.blink() && t.lightLevels[i] != 0 ? partialTick : -partialTick) / 20f), 0f, 1f);
                    break;
            }

            poseStack.pushPose();
            // Rescale it to fit properly in the spots
            poseStack.scale(0.775f, 0.775f, 0f);
            renderer.renderModel(
                    poseStack.last(),
                    // RenderType.debugFilledBox()
                    // multiBufferSource.getBuffer(RenderType.debugQuads()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
                    // multiBufferSource.getBuffer(RenderType.solid()), t.getBlockState(), signalLightModel(), r, g, b, FULLBRIGHT, overlay
                    multiBufferSource.getBuffer(RenderType.beaconBeam(SIGNAL_LIGHT_TEX_LOC, true)), t.getBlockState(), ExtendedSignalsCoreModels.signalLightModel(), r, g, b, SignalRendererHelper.FULLBRIGHT, overlay
                    // multiBufferSource.getBuffer(RenderType.()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
            );
            poseStack.popPose();

            poseStack.translate(0, -7f / 16f, 0);
        }
        //poseStack.popPose();
    }
}
