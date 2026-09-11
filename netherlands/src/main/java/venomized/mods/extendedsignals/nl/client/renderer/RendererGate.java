package venomized.mods.extendedsignals.nl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityCrossingGate;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererCrossingGate;
import venomized.mods.extendedsignals.nl.block.BlockGate;

public class RendererGate<T extends BlockEntityCrossingGate> extends RendererCrossingGate<T> {
    public RendererGate(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void doRender() {
        super.doRender();

        boolean rLeft = blockEntity.getLevel().getGameTime() % 10 > 5;
        boolean rRight = !rLeft;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        poseStack.translate(-1f, 0f, 0f);
        renderLightAt(5.5f / 16f, 32.75f / 16f, -7.75f / 16f, 3.25f, 3.25f, 0f, rLeft && blockEntity.crossingControllerActive() ? 255 : 0, 0, 0);
        renderLightAt(-5.5f / 16f, 32.75f / 16f, -7.75f / 16f, 3.25f, 3.25f, 0f, rRight && blockEntity.crossingControllerActive() ? 255 : 0, 0, 0);
        poseStack.popPose();
    }
}
