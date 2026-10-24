package venomized.mods.extendedsignals.nl;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import venomized.mods.extendedsignals.nl.client.NetherlandModels;

@Mod(value = ExtendedSignalsNetherlands.MOD_ID, dist = Dist.CLIENT)
public class ExtendedSignalsGermanyNetherlands {
    public ExtendedSignalsGermanyNetherlands(IEventBus eventBus) {
        NetherlandModels.init();
    }
}
