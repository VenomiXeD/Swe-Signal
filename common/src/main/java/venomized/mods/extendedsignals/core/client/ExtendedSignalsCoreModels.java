package venomized.mods.extendedsignals.core.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import venomized.mods.extendedsignals.core.ExtendedSignals;

public class ExtendedSignalsCoreModels {
    public static final PartialModel OVERLAY_PATH_LABEL = PartialModel.of(ExtendedSignals.res("block/tracks/overlay_path_label"));
    public static final PartialModel OVERLAY_MODIFIER = PartialModel.of(ExtendedSignals.res("block/tracks/overlay_modifier"));
    public static final PartialModel LIGHT_MODEL = PartialModel.of(ExtendedSignals.res("block/light"));

    public static void init() {
    }
}
