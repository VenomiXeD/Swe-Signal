package venomized.mods.extendedsignals.core.client;

import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.createmod.catnip.data.Couple;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Map;
import java.util.UUID;

public class ClientSignalNetworkCache implements ISignalNetwork {
    private final Map<UUID, Couple<SignalStateNode>> signalEdgeStateMapping = new Object2ReferenceOpenHashMap<>();

    /**
     * @return
     */
    @Override
    public Map<UUID, Couple<SignalStateNode>> signalStateMapping() {
        return this.signalEdgeStateMapping;
    }


    public void fromSync(Map<UUID, Couple<SignalStateNode>> newSynchronizedState) {
        ExtendedSignals.LOGGER.info("Received new full resync update packet: {}", newSynchronizedState.size());
        this.flushAndApplyNewSignalStates(newSynchronizedState);
    }
}
