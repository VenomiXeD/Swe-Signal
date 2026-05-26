package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

@OnlyIn(Dist.CLIENT)
public class RendererSignal<T extends BlockEntitySignal<?>>
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

        RawSignalState rawSignalState = signalBlockEntity.currentSignalState();

        SignalLightPlacement[] lights = signalBlockEntity.getLights();
        if (signalBlockEntity.getSignalDirection() == rawSignalState.getAxisDirection()) {
            ISignalAspect aspect = signalBlockEntity.interpret(rawSignalState);
            aspect.applyAspect(signalBlockEntity.getLevel().getGameTime(), signalBlockEntity.getLightStates());
        }

        for (int i = 0; i < lights.length; i++) {
            if (lights[i] == null)
                continue;

            SignalLightPlacement lightPlacement = lights[i];
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
            SignalLightState state = signalBlockEntity.getLightStates()[i];
            renderer().renderModel(
                    poseStack.last(),
                    multiBufferSource.getBuffer(
                            RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)),
                    signalBlockEntity.getBlockState(),
                    ExtendedSignalsCoreModels.LIGHT_MODEL.get(),
                    state.r(partialTick), state.g(partialTick), state.b(partialTick), 0xFFFFFF, overlay,
                    ModelData.EMPTY, RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)
            );


            poseStack.popPose();
        }
    }

    /**
     * @return
     */
    @Override
    public int getViewDistance() {
        return 8192;
    }
}
