package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import com.simibubi.create.content.trains.track.ITrackBlock;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.IEdgePointBlockEntity;

import java.util.function.Supplier;

public class RendererTrackEdgePointOverlay<T extends BlockEntity & IEdgePointBlockEntity> extends RendererGeneric<T> {
    private PartialModel model;

    /**
     * @param context
     */
    public RendererTrackEdgePointOverlay(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    public static <B extends BlockEntity & IEdgePointBlockEntity> NonNullFunction<BlockEntityRendererProvider.Context, BlockEntityRenderer<? super B>> partialModel(Supplier<PartialModel> modelSupplier) {
        return (ctx) -> {
            RendererTrackEdgePointOverlay<B> overlay = new RendererTrackEdgePointOverlay<>(ctx);
            overlay.model = modelSupplier.get();
            return overlay;
        };
    }

    /**
     *
     */
    @Override
    protected void prepareTransform() {
        BlockPos pos = blockEntity.getBlockPos();
        TrackTargetingBehaviour<?> target = blockEntity.getTrackTargetingBehavior();
        BlockPos targetPosition = target.getGlobalPosition();
        Level level = blockEntity.getLevel();
        BlockState trackState = level.getBlockState(targetPosition);
        Block block = trackState.getBlock();

        if (!(block instanceof ITrackBlock))
            return;
        // if (overlayState == SignalBlockEntity.OverlayState.SKIP)
        // 	return;

        // poseStack.pushPose();
        TransformStack<PoseTransformStack> msr = TransformStack.of(poseStack)
                .translate(targetPosition.subtract(pos));

        // PartialModel partial = track.prepareTrackOverlay(msr, level, pos, trackState, bezier, direction, type);
        ((ITrackBlock) block).prepareTrackOverlay(
                msr,
                level,
                targetPosition,
                trackState,
                target.getTargetBezier(),
                target.getTargetDirection(),
                TrackTargetingBehaviour.RenderedTrackOverlayType.OBSERVER
        );
        super.prepareTransform();
    }

    /**
     *
     */
    @Override
    protected void postRender() {
        CachedBuffers.partial(model, blockEntity.getBlockState())
                .useLevelLight(blockEntity.getLevel())
                .overlay(packedOverlay)
                .renderInto(poseStack, bufferSource.getBuffer(RenderType.CUTOUT_MIPPED));
    }
}
