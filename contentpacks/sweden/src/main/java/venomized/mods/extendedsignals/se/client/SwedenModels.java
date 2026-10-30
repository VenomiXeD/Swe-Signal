package venomized.mods.extendedsignals.se.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;

@OnlyIn(Dist.CLIENT)
public final class SwedenModels {
    public static PartialModel model(String path) {
        return PartialModel.of(ExtendedSignalsSweden.res(path));
    }

    public static final PartialModel ARM_6 = PartialModel.of(ExtendedSignalsSweden.res("block/crossings/se/arm6"));

    public static class SignalModels {

        public static final PartialModel SIGNAL_2L_GANTRY = model("block/signals/se/2l_signal_modern_gantry");
        public static final PartialModel SIGNAL_2L_POLE = model("block/signals/se/2l_signal_modern_pole");
        public static final PartialModel SIGNAL_3L_GANTRY = model("block/signals/se/3l_signal_modern_gantry");
        public static final PartialModel SIGNAL_3L_POLE = model("block/signals/se/3l_signal_modern_pole");
        public static final PartialModel SIGNAL_4L_GANTRY = model("block/signals/se/4l_signal_modern_gantry");
        public static final PartialModel SIGNAL_4L_POLE = model("block/signals/se/4l_signal_modern_pole");
        public static final PartialModel SIGNAL_5L_GANTRY = model("block/signals/se/5l_signal_modern_gantry");
        public static final PartialModel SIGNAL_5L_POLE = model("block/signals/se/5l_signal_modern_pole");
        public static final PartialModel DISTANT_SIGNAL_3L_POLE = model("block/signals/se/3l_distant_signal_modern_pole");

        public static final PartialModel TGOJ_BLOCK_SIGNAL_POLE = model("block/signals/se/tgoj/block_2l_signal_pole");

        public static void init() {
        }
    }

    public static final PartialModel CROSSBUCK_EXTRA = model("block/crossings/se/crossbucks_modern_extra");

    public static void init() {
        SignalModels.init();
    }
}
