package venomized.mods.extendedsignals.se.block.se;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.core.SwSignal;

@OnlyIn(Dist.CLIENT)
public class SeModels {

    public static final PartialModel ARM_4 = PartialModel.of(SwSignal.resource("block/signal/se/crossing/gate_4"));

    public static void init() {
    }
}
