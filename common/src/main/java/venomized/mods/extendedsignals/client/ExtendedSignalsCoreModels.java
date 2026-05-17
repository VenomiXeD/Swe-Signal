package venomized.mods.extendedsignals.client;

import com.google.common.base.Equivalence;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import org.checkerframework.checker.nullness.qual.Nullable;
import venomized.mods.extendedsignals.ExtendedSignalsCore;

public class ExtendedSignalsCoreModels {
    public static final PartialModel BALISE_MODEL = PartialModel.of(ExtendedSignalsCore.res("block/tracks/se_balise"));
    public static final PartialModel LIGHT_MODEL = PartialModel.of(ExtendedSignalsCore.res("block/light"));

    public static void init() {
    }
}
