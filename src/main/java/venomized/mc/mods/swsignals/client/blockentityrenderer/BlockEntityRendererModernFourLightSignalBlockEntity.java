package venomized.mc.mods.swsignals.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import venomized.mc.mods.swsignals.SwSignal;

public class BlockEntityRendererModernFourLightSignalBlockEntity extends BlockEntityRendererSignal {
	public static final ResourceLocation SIGNAL_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/sw_4l_signal_post_1970");
	/**
	 * @return
	 */
	@Override
	public ResourceLocation getSignalModelLoc() {
		return SIGNAL_MODEL_LOC;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isObjModel() {
		return true;
	}
}
