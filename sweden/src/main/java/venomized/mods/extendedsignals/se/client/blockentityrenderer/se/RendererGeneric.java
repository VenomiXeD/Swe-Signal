package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

@OnlyIn(Dist.CLIENT)
public class RendererGeneric<T extends BlockEntity> extends venomized.mods.extendedsignals.client.blockentityrenderer.BlockEntityRendererBase<T> {
    public RendererGeneric(BlockEntityRendererProvider.Context context) {
        super(context);
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
        renderSelfBlock(pBlockEntity, pPoseStack);
        // Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateWithAO(pBlockEntity.getLevel(), getModel(pBlockEntity.getBlockState()), pBlockEntity.getBlockState(), pBlockEntity.getBlockPos(), pPoseStack, pBuffer.getBuffer(RenderType.solid()), true, pBlockEntity.getLevel().getRandom(), pPackedLight, pPackedOverlay);
    }
}