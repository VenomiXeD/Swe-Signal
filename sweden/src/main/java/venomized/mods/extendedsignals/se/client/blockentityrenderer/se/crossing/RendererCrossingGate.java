package venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mods.extendedsignals.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.se.block.SeModels;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.util.MathHelp;

import static venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityCrossingGate.MAX_ARM_MOVEMENT_TICKS;

@OnlyIn(Dist.CLIENT)
public class RendererCrossingGate extends BlockEntityRendererBase<BlockEntityCrossingGate> {
    private static final float ARM_MOVEMENT_TIME = 60f;
    // public static String ARM_5 = ;

    // private final BakedModel MODEL_ARM_5 = Minecraft.getInstance().getModelManager().getModel(SwSignal.modLoc(ARM_5));
//
    public RendererCrossingGate(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BlockEntityCrossingGate pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        renderSelfBlock(
                pBlockEntity,
                pPoseStack
        );
        // getRenderer().tesselateBlock(
        //         pBlockEntity.getLevel(),
        //         this.getModel(pBlockEntity.getBlockState()),
        //         pBlockEntity.getBlockState(),
        //         pBlockEntity.getBlockPos(),
        //         pPoseStack,
        //         pBuffer.getBuffer(RenderType.solid()),
        //         true,
        //         pBlockEntity.getLevel().getRandom(),
        //         pPackedLight,
        //         pPackedOverlay
        // );

        pPoseStack.translate(8f / 16f, 17f / 16f, 8f / 16f);

        final float rotationProgress = (pBlockEntity.getArmMovementProgressTicks() +
                (pPartialTick * (pBlockEntity.isRailroadCrossingControllerPowered() ? -1 : 1))) / MAX_ARM_MOVEMENT_TICKS;

        pPoseStack.mulPose(new Quaternionf(new AxisAngle4f(
                MathHelp.easeInOutBack(rotationProgress, 0.6f) * Mth.HALF_PI, 1, 0, 0)
        ));

        // getRenderer().renderModel(
        // 		pPoseStack.last(),
        // 		pBuffer.getBuffer(RenderType.solid()),
        // 		pBlockEntity.getBlockState(),
        // 		MODEL_ARM_5,
        // 		1,
        // 		1,
        // 		1,
        // 		pPackedLight,
        // 		pPackedOverlay
        // );

        // getRenderer().renderModel(
        //         pPoseStack.last(),
        //         pBuffer.getBuffer(RenderType.solid()),
        //         pBlockEntity.getBlockState(),
        //         SeModels.ARM_4.get(),
        //         1f,1f,1f,
        //         pPackedLight, pPackedOverlay
        // );

        renderer().tesselateWithoutAO(
                pBlockEntity.getLevel(),
                SeModels.ARM_4.get(),
                pBlockEntity.getBlockState(),
                pBlockEntity.getBlockPos().above(),
                pPoseStack,
                pBuffer.getBuffer(RenderType.solid()),
                false,
                pBlockEntity.getLevel().getRandom(),
                pPackedLight,// LightTexture.FULL_BRIGHT,// LevelRenderer.getLightColor(pBlockEntity.getLevel(),pBlockEntity.getBlockPos()),
                pPackedOverlay
        );
    }

    @Override
    public boolean shouldRenderOffScreen(BlockEntityCrossingGate pBlockEntity) {
        return true;
    }


}
