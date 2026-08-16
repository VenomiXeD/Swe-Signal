package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.ClientEvents;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

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
        ISignalAspect aspect = blockEntity.interpret(blockEntity.currentSignalState(), blockEntity.getSignallingDirection());


        renderAdditionalSignals(aspect);

        blockEntity.getSignalLighting().allLights().forEach(light -> light.getState().setIgnoreFadeTicks(!blockEntity.valid()));
        if (!blockEntity.valid()) {
            if (blockEntity.getLevel().getGameTime() % 20 == 0) {
                blockEntity.getSignalLighting().allLights().forEach(light -> light.getState().setColor(255, 0, 0));
            } else {
                blockEntity.getSignalLighting().allLights().forEach(light -> light.getState().setColor(0, 0, 0));
            }
            renderFinalLightValues();
            return;
        }

        if (aspect == null) {
            ExtendedSignals.LOGGER.warn("A Signal block entity somehow returned a null aspect. This should nevever happen, please report this to the developers.\nOffending BlockEntity: {}", blockEntity.getClass().getName());
            return;
        }

        blockEntity.getSignalLighting().allLights().forEach(light -> light.getState().setCurrentTick(blockEntity.getLevel().getGameTime()));
        blockEntity.getSignalLighting().renderFrameBegin();
        aspect.applyAspect(System.nanoTime() / 1_000_000_000f, blockEntity.getSignalLighting());
        renderFinalLightValues();
        blockEntity.getSignalLighting().renderFrameEnd();

    }

    private void renderFinalLightValues() {
        blockEntity.getSignalLighting().allLights().forEach(light -> {
            SignalLight.LightState state = light.getState();
            renderLightAt(
                    light.getX(),
                    light.getY(),
                    light.getZ(),
                    light.getXScale(),
                    light.getYScale(),
                    light.getZScale(),
                    state.getRedOutput(partialTick), state.getGreenOutput(partialTick), state.getBlueOutput(partialTick)
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
