package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate;

public class RendererCrossingGate<T extends BlockEntityCrossingGate> extends RendererGeneric<T> {
    public RendererCrossingGate(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void doRender() {
        super.doRender();
        renderSelfBlock();

        blockEntity.setGateDown(blockEntity.crossingControllerActive());

        PartialModel gateModel = blockEntity.gateArmModel();
        if (gateModel == null)
            return;


        poseStack.pushPose();
        final Vector3f point = blockEntity.gateArmPivotPoint();
        poseStack.translate(point.x, point.y, point.z);
        CachedBuffers.partial(gateModel, blockEntity.getBlockState())
                .rotateCentered(Mth.DEG_TO_RAD * blockEntity.getArmRotation(partialTick), Direction.Axis.X)
                .center()
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(
                        poseStack, bufferSource.getBuffer(RenderType.cutoutMipped())
                );

        poseStack.popPose();
    }
}
