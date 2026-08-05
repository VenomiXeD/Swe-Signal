package venomized.mods.extendedsignals.se.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

@OnlyIn(Dist.CLIENT)
public final class SwedenModels {
    public static final PartialModel ARM_4 = PartialModel.of(ExtendedSignalsSweden.res("block/crossings/se/arm6"));

    public static void init() {
    }
}
