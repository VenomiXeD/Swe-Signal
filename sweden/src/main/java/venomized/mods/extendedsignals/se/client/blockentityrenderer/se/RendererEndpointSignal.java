package venomized.mods.extendedsignals.se.client.blockentityrenderer.se;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.BlockEntityRendererBase;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityEndpointSignal;

@OnlyIn(Dist.CLIENT)
public class RendererEndpointSignal extends BlockEntityRendererBase<BlockEntityEndpointSignal> {
    public RendererEndpointSignal(BlockEntityRendererProvider.Context context) {
    }
}
