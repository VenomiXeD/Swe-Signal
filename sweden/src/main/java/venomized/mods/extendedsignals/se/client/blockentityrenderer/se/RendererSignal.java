package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.client.blockentityrenderer.SignalRendererHelper;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.se.blockentity.mainsignals.BlockEntitySignal;

@OnlyIn(Dist.CLIENT)
public class RendererSignal<T extends BlockEntitySignal>
        extends BlockEntityRendererBase<T> {
    public RendererSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    public RendererSignal() {
        super();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull T pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(@NotNull T pBlockEntity, @NotNull Vec3 pCameraPos) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(T signalBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light,
                       int overlay) {
        super.render(
                signalBlockEntity,
                partialTick,
                poseStack,
                multiBufferSource,
                light,
                overlay
        );

        renderSelfBlock(
                signalBlockEntity,
                poseStack
        );
        renderSignalLights(signalBlockEntity, partialTick, poseStack, multiBufferSource, light, overlay);
    }

    private void renderSignalLights(T signalBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light,
                                    int overlay) {
        for (SignalLightPlacement lightPlacement : signalBlockEntity.getLights()) {
            poseStack.pushPose();
            poseStack.translate(
                    lightPlacement.getX() + 0.5d,
                    lightPlacement.getY(),
                    lightPlacement.getZ() + 0.5d
            );

            poseStack.scale(
                    lightPlacement.getXScale() / 2f,
                    lightPlacement.getYScale() / 2f,
                    lightPlacement.getZScale() / 2f
            );

            renderer().renderModel(
                    poseStack.last(),
                    multiBufferSource.getBuffer(
                            RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                    signalBlockEntity.getBlockState(),
                    ExtendedSignalsCoreModels.LIGHT_MODEL.get(),
                    1, 1, 1, 0xFFFFFF, overlay, ModelData.EMPTY, RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)
            );

            poseStack.popPose();
        }
    }
}
