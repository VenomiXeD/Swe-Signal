package venomized.mods.extendedsignals.se.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

@OnlyIn(Dist.CLIENT)
public final class SwedenModels {
    public static final PartialModel ARM_6 = PartialModel.of(ExtendedSignalsSweden.res("block/crossings/se/arm6"));

    public static final PartialModel SIGNAL_2L_GANTRY = PartialModel.of(ExtendedSignalsSweden.res("block/signals/se/2l_signal_modern_gantry"));
    public static final PartialModel SIGNAL_3L_GANTRY = PartialModel.of(ExtendedSignalsSweden.res("block/signals/se/3l_signal_modern_gantry"));
    public static final PartialModel SIGNAL_4L_GANTRY = PartialModel.of(ExtendedSignalsSweden.res("block/signals/se/4l_signal_modern_gantry"));
    public static final PartialModel SIGNAL_5L_GANTRY = PartialModel.of(ExtendedSignalsSweden.res("block/signals/se/5l_signal_modern_gantry"));

    public static void init() {
    }
}
