package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.util.MathHelp;

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

    public void renderAdditionalSignals(ISignalAspect aspect, SignalContainer signalLights) {

    }

    private void renderSignalLights() {
        ISignalAspect aspect = blockEntity.interpret(blockEntity.currentSignalState(), blockEntity.getSignallingDirection());


        final SignalContainer signals = blockEntity.getSignalContainer();
        signals.allLights().forEach(light -> light.getState().setIgnoreFadeTicks(!blockEntity.isSignalValid()));
        if (!blockEntity.isSignalValid()) {
            if (blockEntity.getLevel().getGameTime() % 20 == 0) {
                blockEntity.getSignalContainer().allLights().forEach(light -> light.getState().setColor(255, 0, 0));
            } else {
                blockEntity.getSignalContainer().allLights().forEach(light -> light.getState().setColor(0, 0, 0));
            }
            renderFinalLightValues();
            return;
        }

        if (aspect == null) {
            ExtendedSignals.LOGGER.warn("A Signal block entity somehow returned a null aspect. This should nevever happen, please report this to the developers.\nOffending BlockEntity: {}", blockEntity.getClass().getName());
            return;
        }

        signals.allLights().forEach(light -> light.getState().setCurrentTick(blockEntity.getLevel().getGameTime()));
        signals.renderFrameBegin();
        renderAdditionalSignals(aspect, signals);
        aspect.applyAspect(System.nanoTime() / 1_000_000_000f, blockEntity.getSignalContainer());
        renderFinalLightValues();
        signals.renderFrameEnd();

    }

    private void renderFinalLightValues() {
        blockEntity.getSignalContainer().allLights().forEach(light -> {
            SignalLight.LightState state = light.getState();

            int r = state.getRedOutput(partialTick);
            int g = state.getGreenOutput(partialTick);
            int b = state.getBlueOutput(partialTick);
            renderLightAtWithFlare(
                    light.getX(),
                    light.getY(),
                    light.getZ(),
                    light.getXScale(),
                    light.getYScale(),
                    light.getZScale(),
                    r, g, b, MathHelp.maxOf(r, g, b)
            );
        });
    }

    /**
     * @return
     */
    @Override
    public int getViewDistance() {
        return 512;
    }
}
