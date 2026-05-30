package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@OnlyIn(Dist.CLIENT)
public class RendererSignal<T extends BlockEntitySignal<?>>
        extends BlockEntityRendererBase<T> {
    public RendererSignal(BlockEntityRendererProvider.Context context) {
        super(context);
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
            SignalLightState state = blockEntity.getLightStates()[i];
            CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
                    .color((int) (state.r(partialTick) * 255), (int) (state.g(partialTick) * 255), (int) (state.b(partialTick) * 255), 255)
                    .overlay(packedOverlay)
                    .disableDiffuse()
                    .light(0xFFFFFF)
                    .renderInto(
                            poseStack,
                            bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)
                            )
                    );


            poseStack.popPose();
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
