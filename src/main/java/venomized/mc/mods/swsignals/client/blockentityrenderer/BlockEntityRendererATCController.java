package venomized.mc.mods.swsignals.client.blockentityrenderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntityATCController;
import venomized.mc.mods.swsignals.create.tracks.ATCController;

public class BlockEntityRendererATCController extends SafeBlockEntityRenderer<BlockEntityATCController> {

	public BlockEntityRendererATCController(BlockEntityRendererProvider.Context context) {}

	@Override
	public boolean shouldRenderOffScreen(BlockEntityATCController pBlockEntity) {
		return true;
	}



	@Override
	protected void renderSafe(BlockEntityATCController be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
		BlockPos pos = be.getBlockPos();
		TrackTargetingBehaviour<ATCController> target = be.atcControllerPoint;
		BlockPos targetPosition = target.getGlobalPosition();
		Level level = be.getLevel();
		BlockState trackState = level.getBlockState(targetPosition);
		Block block = trackState.getBlock();

		if (!(block instanceof ITrackBlock))
			return;
		// if (overlayState == SignalBlockEntity.OverlayState.SKIP)
		// 	return;

		ms.pushPose();
		TransformStack.cast(ms)
				.translate(targetPosition.subtract(pos));

		((ITrackBlock)block).prepareTrackOverlay(
				level,
				targetPosition,
				trackState,
				target.getTargetBezier(),
				target.getTargetDirection(),
				ms,
				TrackTargetingBehaviour.RenderedTrackOverlayType.SIGNAL
		);

		ms.translate(0f,2f/16f,0f);

		Minecraft.getInstance().getBlockRenderer().getModelRenderer()
						.tesselateWithAO(
								level,
								Minecraft.getInstance().getModelManager().getModel(SwSignal.modLoc("block/tracks/se_balise")),
								trackState,
								targetPosition,
								ms,
								bufferSource.getBuffer(RenderType.solid()),
								false,
								level.random,
								light,
								overlay
						);

		// TrackTargetingBehaviour.RenderedTrackOverlayType type = overlayState == SignalBlockEntity.OverlayState.DUAL ? TrackTargetingBehaviour.RenderedTrackOverlayType.DUAL_SIGNAL : TrackTargetingBehaviour.RenderedTrackOverlayType.SIGNAL;
		// TrackTargetingBehaviour.render(level, targetPosition, atcControllerPoint.getTargetDirection(), atcControllerPoint.getTargetBezier(), ms,
				// bufferSource, light, overlay, TrackTargetingBehaviour.RenderedTrackOverlayType.SIGNAL, 1);
		ms.popPose();
	}
}
