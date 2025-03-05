package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRendererModernFiveLightSignal extends BlockEntityRendererSignal {
	/**
	 * @return
	 */
	@Override
	public boolean isObjModel() {
		return true;
	}
}
