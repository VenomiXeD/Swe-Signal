package venomized.mods.extendedsignals.de;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import venomized.mods.extendedsignals.de.client.GermanyModels;

@Mod(value = ExtendedSignalsGermany.MOD_ID, dist = Dist.CLIENT)
public class ExtendedSignalsGermanyClient {
    public ExtendedSignalsGermanyClient(IEventBus eventBus) {
        GermanyModels.init();
    }
}
