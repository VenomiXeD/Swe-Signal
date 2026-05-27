package venomized.mods.extendedsignals.core.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Map;
import java.util.UUID;

public class ClientSignalNetworkCache implements ISignalNetwork {
    private final Object2ObjectMap<UUID, SignalStateNode> signalEdgeStateMapping = new Object2ObjectOpenHashMap<>();

    /**
     * @return
     */
    @Override
    public Object2ObjectMap<UUID, SignalStateNode> signalStates() {
        return this.signalEdgeStateMapping;
    }


    public void fromSync(Map<UUID, SignalStateNode> newSynchronizedState) {
        ExtendedSignalsCore.LOGGER.info("Received new full resync update packet: {}", newSynchronizedState.size());
        this.flushAndApplyNewSignalStates(newSynchronizedState);
    }
}
