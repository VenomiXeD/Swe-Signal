package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.se.BlockSignal;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

@OnlyIn(Dist.CLIENT)
public class RendererSignal<T extends BlockEntitySignal>
        extends BlockEntityRendererBase<T> {
    protected static final int FULLBRIGHT = 0xFFFFFF;
    public static ResourceLocation SIGNAL_LIGHT_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,
            "block/light");
    public static ResourceLocation SIGNAL_LIGHT_TEX_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,
            "textures/block/light.png");
    private static BakedModel SIGNAL_LIGHT_MODEL;

    public RendererSignal(BlockEntityRendererProvider.Context context) {
    }

    public static BakedModel signalLightModel() {
        if (SIGNAL_LIGHT_MODEL == null) {
            SIGNAL_LIGHT_MODEL = Minecraft.getInstance().getModelManager().getModel(SIGNAL_LIGHT_MODEL_LOC);
        }
        return SIGNAL_LIGHT_MODEL;
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
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(T t, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light,
                       int overlay) {
        super.render(t, partialTick, poseStack, multiBufferSource, light, overlay);

        int lightCount = t.getLightCount();

        ModelBlockRenderer renderer = Minecraft.getInstance().getBlockRenderer().getModelRenderer();

        if (t.getBlockState().getValue(BlockSignal.MOUNTED)) {
            poseStack.translate(0, 8 / 16d, 0);
        }

        // What a garbage way to center something
        if (this.isObjModel()) {
            poseStack.pushPose();
            poseStack.translate(.5, 0, .5);
        }

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
                overlay);

        if (this.isObjModel()) {
            poseStack.popPose();
        }

        poseStack.translate(.5d, 0d, .5d);
        poseStack.translate(0, 2.75f / 16d, -5.6d / 16d);

        SwedishSignalAspect aspect = t.getCurrentDisplayingAspect();
        // t.clientTick(partialTick, aspect, t.getCurrentDisplayingState(), !t.valid() || aspect == null);

        poseStack.translate(0, 0.5d * (lightCount - 1), 0);

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
            poseStack.scale(1.1f, 1.1f, 0.1f);
            renderer.renderModel(
                    poseStack.last(),
                    // RenderType.debugFilledBox()
                    // multiBufferSource.getBuffer(RenderType.debugQuads()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
                    // multiBufferSource.getBuffer(RenderType.solid()), t.getBlockState(), signalLightModel(), r, g, b, FULLBRIGHT, overlay
                    multiBufferSource.getBuffer(RenderType.beaconBeam(SIGNAL_LIGHT_TEX_LOC, true)), t.getBlockState(), signalLightModel(), r, g, b, FULLBRIGHT, overlay
                    // multiBufferSource.getBuffer(RenderType.()), t.getBlockState(), this.signalLightModel(), r,g,b, FULLBRIGHT, overlay
            );
            poseStack.popPose();

            poseStack.translate(0, -8 / 16d, 0);
        }
        //poseStack.popPose();
    }
}
