package venomized.mods.extendedsignals.de.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

@OnlyIn(Dist.CLIENT)
public class GermanyModels {
    public static PartialModel ZS3 = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/zs3")
    );

    public static PartialModel ZS3V = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/zs3v")
    );

    public static void init() {

    }
}
