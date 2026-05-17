package venomized.mods.extendedsignals.se.block;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

@OnlyIn(Dist.CLIENT)
public class SeModels {

    public static final PartialModel ARM_4 = PartialModel.of(ExtendedSignalsSweden.res("block/signal/se/crossing/gate_4"));

    public static void init() {
    }
}
