package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

@OnlyIn(Dist.CLIENT)
public class RendererGeneric<T extends BlockEntity> extends BlockEntityRendererBase<T> {
    protected final ResourceLocation MODEL_LOC;

    public RendererGeneric(String resourceLoc) {
        this.MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, resourceLoc);
    }

    /**
     * A generic renderer - will use the block model supplied by the BlockState model
     */
    public RendererGeneric() {
        this.MODEL_LOC = null;
    }

    public RendererGeneric(BlockEntityRendererProvider.Context context) {
        this();
    }

    /**
     * @return
     */
    @Override
    protected ResourceLocation modelLoc() {
        return MODEL_LOC;
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
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithAO(pBlockEntity.getLevel(), getModel(pBlockEntity.getBlockState()), pBlockEntity.getBlockState(), pBlockEntity.getBlockPos(), pPoseStack, pBuffer.getBuffer(RenderType.solid()), true, pBlockEntity.getLevel().getRandom(), pPackedLight, pPackedOverlay);
    }
}