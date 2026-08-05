package venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityCrossingDistantSignal;

public class RendererCrossingDistantSignal<T extends BlockEntityCrossingDistantSignal> extends RendererGeneric<T> {
    public RendererCrossingDistantSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void doRender() {
        boolean lit = blockEntity.getLevel().getGameTime() % 40 < 20 || this.blockEntity.isActive();
        super.doRender();
        renderSelfBlock();

        int redPower = blockEntity.isActive() ? 255 : 0;
        boolean alternate = blockEntity.getLevel().getGameTime() % 20 < 10;
        // 255, 191, 0
        int r = lit ? 255 : 0;
        int g = lit ? 191 : 0;
        int b = 0;
        renderLightAt(
                0, 5.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                r,
                g,
                b
        );
        renderLightAt(
                4f / 16f, 12.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                r,
                g,
                b
        );
        renderLightAt(
                -4f / 16f, 12.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                r,
                g,
                b
        );
    }
}
