package venomized.mods.extendedsignals.se;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import venomized.mods.extendedsignals.se.client.SwedenModels;

@Mod(value = ExtendedSignalsSweden.MOD_ID, dist = Dist.CLIENT)
public class ExtendedSignalsSwedenClient {
    public ExtendedSignalsSwedenClient(IEventBus bus) {
        SwedenModels.init();
    }
}
