package venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.se.blockentity.signals.auxillary.BlockEntityCrossingDistantSignal;

public class RendererCrossingDistantSignal<T extends BlockEntityCrossingDistantSignal> extends RendererGeneric<T> {
    public RendererCrossingDistantSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void doRender() {
        boolean lit = this.blockEntity.crossingControllerActive();
        float brightness = lit ? 1 : (float) ((Math.sin(2.0 * Math.PI * (blockEntity.getLevel().getGameTime() + partialTick) / 15.0) + 1.0) / 2.0);
        super.doRender();
        renderSelfBlock();

        int redPower = blockEntity.crossingControllerActive() ? 255 : 0;
        boolean alternate = blockEntity.getLevel().getGameTime() % 20 < 10;
        // 255, 191, 0
        int r = (int) (brightness * 255);
        int g = (int) (brightness * 191);
        int b = 0;
        renderLightAtWithFlare(
                0, 5.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                r,
                g,
                b, 255
        );
        renderLightAtWithFlare(
                4f / 16f, 12.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                r,
                g,
                b, 255
        );
        renderLightAtWithFlare(
                -4f / 16f, 12.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                r,
                g,
                b, 255
        );
    }
}
