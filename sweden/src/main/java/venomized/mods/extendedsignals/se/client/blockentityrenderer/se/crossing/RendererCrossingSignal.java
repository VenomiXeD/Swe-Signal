package venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityCrossingSignal;

public class RendererCrossingSignal<T extends BlockEntityCrossingSignal> extends RendererGeneric<T> {
    public RendererCrossingSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void doRender() {
        super.doRender();
        super.renderSelfBlock();

        int r = 255;
        int g = blockEntity.isActive() ? 255 : 0;
        int b = blockEntity.isActive() ? 255 : 0;

        renderLightAt(
                0f,
                1.2282f,
                0.48f,
                5, 5, 0, r, g, b
        );

        renderLightAt(
                0f,
                1.2282f,
                -0.125f,
                5, 5, 0, r, g, b
        );
    }
}
