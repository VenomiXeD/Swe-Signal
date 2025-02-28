package venomized.mc.mods.swsignals.client.blockentityrenderer;

import net.minecraft.resources.ResourceLocation;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntityUSign;

public class BlockEntityRendererUSign extends SwAbstractBlockEntityBasicModelRenderer<BlockEntityUSign> {
	public static final ResourceLocation MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/sw_u_sign");

	/**
	 * @return
	 */
	@Override
	protected ResourceLocation modelLoc() {
		return MODEL_LOC;
	}
}