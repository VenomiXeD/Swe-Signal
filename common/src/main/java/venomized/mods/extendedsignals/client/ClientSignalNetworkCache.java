package venomized.mods.extendedsignals.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.RawSignalState;
import venomized.mods.extendedsignals.core.ISignalNetwork;

import java.util.Map;
import java.util.UUID;

public class ClientSignalNetworkCache implements ISignalNetwork {
    private Object2ObjectMap<UUID, RawSignalState> signalStates = new Object2ObjectOpenHashMap<>();

    /**
     * @return
     */
    @Override
    public Object2ObjectMap<UUID, RawSignalState> signalStates() {
       return this.signalStates;
    }


    public void fromSync(Map<UUID, RawSignalState> newSynchronizedState) {
        ExtendedSignalsCore.LOGGER.info("Received new full resync update packet: {}", newSynchronizedState.size());
        this.flushAndApplyNewSignalStates(newSynchronizedState);
    }
}
