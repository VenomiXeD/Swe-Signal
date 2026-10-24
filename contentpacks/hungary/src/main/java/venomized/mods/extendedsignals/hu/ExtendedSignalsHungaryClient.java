package venomized.mods.extendedsignals.hu;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import venomized.mods.extendedsignals.hu.client.HungaryModels;

@Mod(value = ExtendedSignalsHungary.MOD_ID, dist = Dist.CLIENT)
public class ExtendedSignalsHungaryClient {
    public ExtendedSignalsHungaryClient(IEventBus eventBus) {
        HungaryModels.init();
    }
}
