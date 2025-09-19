package venomized.mc.mods.swsignals.client.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.block.Sw45DegreeBlock;
import venomized.mc.mods.swsignals.block.se.BlockModernTwoLightSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.se.RendererSignal;

@OnlyIn(Dist.CLIENT)
public abstract class BlockEntityRendererBase<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private BakedModel cachedModel;

    public BlockEntityRendererBase() {
    }

    protected static ModelBlockRenderer getRenderer() {
        return Minecraft.getInstance().getBlockRenderer().getModelRenderer();
    }

    /**
     * Renders a single light at the given position with the given scale and color.
     *
     * @param pMainBlockEntity
     * @param pPoseStack
     * @param pBuffer
     * @param pPackedOverlay
     * @param x
     * @param y
     * @param z
     * @param sx
     * @param sy
     * @param sz
     * @param r
     * @param g
     * @param b
     */
    protected static void renderLight(BlockEntity pMainBlockEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedOverlay, float x, float y, float z, float sx, float sy, float sz, float r, float g, float b) {
        pPoseStack.pushPose();
        pPoseStack.translate(x, y, z);
        pPoseStack.scale(sx, sy, sz);
        getRenderer().renderModel(
                pPoseStack.last(),
                pBuffer.getBuffer(RenderType.beaconBeam(RendererSignal.SIGNAL_LIGHT_TEX_LOC, true)),
                pMainBlockEntity.getBlockState(),
                RendererSignal.signalLightModel(),
                r, g, b,
                0xFFFFFF,
                pPackedOverlay
        );
        pPoseStack.popPose();
    }

    protected static void renderLight(BlockEntity pMainBlockEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedOverlay, float x, float y, float z, float r, float g, float b) {
        renderLight(
                pMainBlockEntity,
                pPoseStack,
                pBuffer,
                pPackedOverlay,
                x, y, z,
                1f, 1f, 1f,
                r, g, b
        );
    }

    public boolean isObjModel() {
        return false;
    }

    protected ResourceLocation modLoc(String p) {
        return ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, p);
    }

    @Override
    public int getViewDistance() {
        return 1024;
    }

    protected ResourceLocation modelLoc() {
        return null;
    }

    protected BakedModel getModel(BlockState currentBlockState) {
        if (cachedModel == null) {
            ResourceLocation modelLoc = modelLoc();
            if (modelLoc == null) {
                cachedModel = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(currentBlockState);
            } else {
                Minecraft mc = Minecraft.getInstance();
                cachedModel = mc.getModelManager().getModel(modelLoc);
            }
        }
        return cachedModel;
    }

    protected void renderSelfBlock(T pBlockEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        getRenderer().tesselateWithAO(
                pBlockEntity.getLevel(),
                getModel(pBlockEntity.getBlockState()),
                pBlockEntity.getBlockState(),
                pBlockEntity.getBlockPos(),
                pPoseStack,
                pBuffer.getBuffer(RenderType.solid()),
                true,
                pBlockEntity.getLevel().getRandom(),
                pPackedLight,
                pPackedOverlay
        );
    }

    /**
     * @param pBlockEntity
     * @param pPartialTick
     * @param pPoseStack
     * @param pBuffer
     * @param pPackedLight
     * @param pPackedOverlay
     */
    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getBlockState().hasProperty(Sw45DegreeBlock.ORIENTATION)) {
            pPoseStack.rotateAround(
                    new Quaternionf(
                            new AxisAngle4f(pBlockEntity.getBlockState().getValue(BlockModernTwoLightSignal.ORIENTATION) / -4f * Mth.PI, 0, 1, 0)), .5f, 0, .5f
            );
        }
    }
}
