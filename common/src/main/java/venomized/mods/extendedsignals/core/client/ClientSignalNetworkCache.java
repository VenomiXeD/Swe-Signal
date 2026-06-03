package venomized.mods.extendedsignals.core.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.createmod.catnip.data.Couple;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Map;
import java.util.UUID;

public class ClientSignalNetworkCache implements ISignalNetwork {
    private final Object2ObjectMap<UUID, Couple<SignalStateNode>> signalEdgeStateMapping = new Object2ObjectOpenHashMap<>();

    /**
     * @return
     */
    @Override
    public Object2ObjectMap<UUID, Couple<SignalStateNode>> signalStates() {
        return this.signalEdgeStateMapping;
    }


    public void fromSync(Map<UUID, Couple<SignalStateNode>> newSynchronizedState) {
        ExtendedSignalsCore.LOGGER.info("Received new full resync update packet: {}", newSynchronizedState.size());
        this.flushAndApplyNewSignalStates(newSynchronizedState);
    }
}
