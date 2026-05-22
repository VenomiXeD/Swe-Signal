package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererModernFiveLightSignal extends RendererSignal {
    public RendererModernFiveLightSignal(BlockEntityRendererProvider.Context context) {
        super(context);

    }

    /**
     * @return
     */
    private boolean isObjModel() {
        return true;
    }
}
