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
    }
}
