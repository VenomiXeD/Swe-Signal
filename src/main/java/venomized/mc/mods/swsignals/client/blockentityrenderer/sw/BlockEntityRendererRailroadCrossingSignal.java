package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityRailroadCrossingSignal;
import venomized.mc.mods.swsignals.client.blockentityrenderer.BlockEntityRendererBase;

public class BlockEntityRendererRailroadCrossingSignal extends BlockEntityRendererBase<BlockEntityRailroadCrossingSignal> {
	@Override
	protected ResourceLocation modelLoc() {
		return modLoc("block/sw_railroadcrossing_signal_2");
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
	public void render(BlockEntityRailroadCrossingSignal pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
		super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

		getRenderer()
				.tesselateWithAO(
						pBlockEntity.getLevel(),
						getModel(),
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
}
