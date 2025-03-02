package venomized.mc.mods.swsignals.client.blockentityrenderer.sw;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntityThreeLightSignal;

@OnlyIn(Dist.CLIENT)
public class BlockEntityRendererModernThreeLightSignalBlockEntity extends BlockEntityRendererSignal<BlockEntityThreeLightSignal> {
	public static final ResourceLocation SIGNAL_MODEL_LOC = ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID,"block/sw_3l_signal_post_1970");

	/**
	 * @return
	 */
	@Override
	public ResourceLocation getSignalModelLoc() {
		return SIGNAL_MODEL_LOC;
	}
}
