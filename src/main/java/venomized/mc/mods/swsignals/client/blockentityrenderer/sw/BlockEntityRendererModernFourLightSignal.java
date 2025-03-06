package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRendererModernFourLightSignal extends BlockEntityRendererSignal {
	/**
	 * @return
	 */
	@Override
	public boolean isObjModel() {
		return true;
	}
}
