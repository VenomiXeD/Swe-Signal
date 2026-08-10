package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityKs;

public class RendererKs<T extends BlockEntityKs<?>> extends RendererSignal<T> {
    public RendererKs(BlockEntityRendererProvider.Context context) {
        super(context);
    }
}
