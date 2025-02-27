package venomized.mc.mods.swsignals.client.blockentityrenderer;

import net.minecraft.resources.ResourceLocation;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.BlockEntityThreeLightSignalBlock;

public class BlockEntityRendererModernThreeLightSignalBlockEntity extends BlockEntityRendererSignal<BlockEntityThreeLightSignalBlock> {
	public static final ResourceLocation SIGNAL_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/sw_3l_signal_post_1970");

	/**
	 * @return
	 */
	@Override
	public ResourceLocation getSignalModelLoc() {
		return SIGNAL_MODEL_LOC;
	}
}
