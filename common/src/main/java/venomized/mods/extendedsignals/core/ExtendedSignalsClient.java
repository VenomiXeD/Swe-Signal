package venomized.mods.extendedsignals.core;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import venomized.mods.extendedsignals.core.client.ClientEvents;
import venomized.mods.extendedsignals.core.client.ClientSignalNetworkCache;
import venomized.mods.extendedsignals.core.client.ExtendedSignalsCoreModels;

@Mod(value = ExtendedSignals.MOD_ID, dist = Dist.CLIENT)
public class ExtendedSignalsClient {
    public ExtendedSignalsClient(IEventBus bus) {
        NeoForge.EVENT_BUS.register(ClientEvents.class);

        ExtendedSignals.EXTENDED_SIGNAL_CLIENT_CACHE = new ClientSignalNetworkCache();
        ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY = ExtendedSignals.EXTENDED_SIGNAL_CLIENT_CACHE;

        ExtendedSignalsCoreModels.init();
    }
}
