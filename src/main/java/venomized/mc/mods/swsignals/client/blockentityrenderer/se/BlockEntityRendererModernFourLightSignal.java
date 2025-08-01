package venomized.mc.mods.swsignals.client.blockentityrenderer.se;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRendererModernFourLightSignal extends BlockEntityRendererSignal {
	public BlockEntityRendererModernFourLightSignal(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	/**
	 * @return
	 */
	@Override
	public boolean isObjModel() {
		return true;
	}
}
