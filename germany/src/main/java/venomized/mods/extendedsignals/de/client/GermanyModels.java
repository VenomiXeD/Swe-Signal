package venomized.mods.extendedsignals.de.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

@OnlyIn(Dist.CLIENT)
public final class GermanyModels {
    public static PartialModel ZS3 = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/zs3")
    );

    public static PartialModel ZS3V = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/zs3v")
    );

    public static PartialModel KS_VR_400_RIGHT = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/ks_vr_400_right")
    );

    public static void init() {

    }
}
