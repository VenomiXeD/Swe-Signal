package venomized.mods.extendedsignals.core.client.blockentityrenderer;

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

        CachedBuffers.partial(blockEntity.getCrossingArmModel(), blockEntity.getBlockState())
                .center()
                .translate(0, -0.5f + blockEntity.getArmRotationHeightPoint(), 0)
                .rotate(Direction.Axis.X, Mth.DEG_TO_RAD * blockEntity.getArmRotation(partialTick))
                // .translate(new Vec3(0,0,3.5f/16f))
                .light(packedLight)
                .overlay(packedOverlay)
                .renderInto(
                        poseStack, bufferSource.getBuffer(RenderType.cutoutMipped())
                );
    }
}
