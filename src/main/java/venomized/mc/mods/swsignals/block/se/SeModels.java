package venomized.mc.mods.swsignals.block.se;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.core.SwSignal;

@OnlyIn(Dist.CLIENT)
public class SeModels {

    public static final PartialModel ARM_4 = PartialModel.of(SwSignal.resource("block/signal/se/crossing/gate_4"));

    public static void init() {
    }
}
