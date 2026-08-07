package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsConfig;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@OnlyIn(Dist.CLIENT)
public class RendererSignal<T extends BlockEntitySignal<?>>
        extends RendererGeneric<T> {
    public RendererSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * @param blockEntity
     * @return
     */
    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return super.getRenderBoundingBox(blockEntity).inflate(5f);
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

        renderAdditionalModels();
        renderSelfBlock();
        renderSignalLights();
    }

    public void renderAdditionalModels() {

    }

    public void renderAdditionalSignals(ISignalAspect aspect) {

    }

    private void renderSignalLights() {

        SignalLightPlacement[] lights = blockEntity.getLights();

        SignalStateNode signalStateNode = blockEntity.currentSignalState();
        ISignalAspect aspect = blockEntity.interpret(signalStateNode, blockEntity.getSignallingDirection());
        if (aspect == null) {
            ExtendedSignals.LOGGER.warn("A Signal block entity somehow returned a null aspect. This should nevever happen, please report this to the developers.\nOffending BlockEntity: {}", blockEntity.getClass().getName());
            return;
        }

        if (!blockEntity.valid()) {
            if (blockEntity.getLevel().getGameTime() % 20 == 0) {
                for (SignalLightState lightState : blockEntity.getLightStates()) {
                    lightState.setColorDirect(1, 0, 0);
                }
            } else {
                for (SignalLightState lightState : blockEntity.getLightStates()) {
                    lightState.setColorDirect(0, 0, 0);
                }
            }
        } else {
            aspect.applyAspect(blockEntity.getLevel().getGameTime(), blockEntity.getLightStates());
        }
        renderAdditionalSignals(aspect);

        for (int i = 0; i < lights.length; i++) {
            if (lights[i] == null)
                continue;

            SignalLightPlacement lightPlacement = lights[i];
            SignalLightState state = blockEntity.getLightStates()[i];
            renderLightAt(
                    lightPlacement.getX(),
                    lightPlacement.getY(),
                    lightPlacement.getZ(),
                    lightPlacement.getXScale(),
                    lightPlacement.getYScale(),
                    lightPlacement.getZScale(),
                    (byte) (state.r(partialTick) * 255), (byte) (state.g(partialTick) * 255), (byte) (state.b(partialTick) * 255)

            );
            // poseStack.pushPose();
            // poseStack.translate(
            //         lightPlacement.getX() + 0.5d,
            //         lightPlacement.getY(),
            //         lightPlacement.getZ() + 0.5d
            // );

            // poseStack.scale(
            //         lightPlacement.getXScale() / 2f,
            //         lightPlacement.getYScale() / 2f,
            //         lightPlacement.getZScale() / 2f
            // );

            // CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
            //         .color(, 255)
            //         .overlay(packedOverlay)
            //         .disableDiffuse()
            //         .light(0xFFFFFF)
            //         .renderInto(
            //                 poseStack,
            //                 bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)
            //                 )
            //         );


            // poseStack.popPose();
        }
    }

    /**
     * @return
     */
    @Override
    public int getViewDistance() {
        return 2048;
    }
}
