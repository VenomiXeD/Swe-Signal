package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate;

public class RendererCrossingGate<T extends BlockEntityCrossingGate> extends RendererGeneric<T> {
    public RendererCrossingGate(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void doRender() {
        super.doRender();
        renderSelfBlock();

        blockEntity.setGateDown(blockEntity.isActive());

        PartialModel gateModel = blockEntity.getCrossingArmModel();
        if (gateModel == null)
            return;


        poseStack.pushPose();
        poseStack.translate(0, blockEntity.getArmRotationHeightPoint(), 0);
        CachedBuffers.partial(gateModel, blockEntity.getBlockState())
                // .translate(0, -0.5f + blockEntity.getArmRotationHeightPoint(), 0)
                .rotateCentered(Mth.DEG_TO_RAD * blockEntity.getArmRotation(partialTick), Direction.Axis.X)
                .center()
                // .translate(new Vec3(0,0,3.5f/16f))
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(
                        poseStack, bufferSource.getBuffer(RenderType.cutoutMipped())
                );

        poseStack.popPose();
    }
}
