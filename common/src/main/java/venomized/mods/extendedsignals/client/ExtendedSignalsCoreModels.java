package venomized.mods.extendedsignals.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.resources.model.BakedModel;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;

public class ExtendedSignalsCoreModels {
    public static final PartialModel BALISE_MODEL = PartialModel.of(ExtendedSignalsCore.res("block/tracks/se_balise"));
    public static final PartialModel LIGHT_MODEL = PartialModel.of(ExtendedSignalsCore.res("block/light"));

    public static void init() {
    }

    public static BakedModel signalLightModel() {
        return LIGHT_MODEL.get();
    }
}
