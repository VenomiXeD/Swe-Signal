package venomized.mods.extendedsignals.core.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.resources.model.BakedModel;
import venomized.mods.extendedsignals.core.ExtendedSignals;

public class ExtendedSignalsCoreModels {
    public static final PartialModel BALISE_MODEL = PartialModel.of(ExtendedSignals.res("block/tracks/se_balise"));
    public static final PartialModel LIGHT_MODEL = PartialModel.of(ExtendedSignals.res("block/light"));

    public static void init() {
    }
}
