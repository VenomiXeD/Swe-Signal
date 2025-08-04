package venomized.mc.mods.swsignals.block.se;

import com.jozufozu.flywheel.core.PartialModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;

public class SeModels {
	@OnlyIn(Dist.CLIENT)
	public static final PartialModel ARM_5 = new PartialModel(SwSignal.modLoc("block/signals/se/crossing/gate_5"));

	public static void init() {
	}
}
