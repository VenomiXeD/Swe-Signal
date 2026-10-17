package venomized.mods.extendedsignals.core;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.graph.TrackGraph;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ArrayListDeque;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundSyncSignalStatePacket;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.*;

/**
 * A class that represents the signal network for both the client and server.
 */
public class ServerSignalNetworkCache extends SavedData implements ISignalNetwork {
    private static final String NAME = "extended_signals_signal_mapping_data";

    private final Map<UUID, Couple<SignalStateNode>> signalStateMapping = new Object2ReferenceOpenHashMap<>();
    private final Map<UUID, Couple<Integer>> signalStateNetworkHashes = new Object2ReferenceOpenHashMap<>();
    private final Queue<SignalSyncEntry> queuedUpdates = new ArrayListDeque<>();

    public static ServerSignalNetworkCache get(final MinecraftServer server) {
        ExtendedSignals.LOGGER.info("Extended Signals is loading signal data...");
        ServerSignalNetworkCache data = server.overworld().getDataStorage().computeIfAbsent(
                new Factory<>(
                        ServerSignalNetworkCache::create,
                        ServerSignalNetworkCache::load
                ),
                NAME
        );

        ExtendedSignals.EXTENDED_SIGNAL_CACHE_PROXY = data;

        return data;
    }

    private static ServerSignalNetworkCache load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ServerSignalNetworkCache serverSignalNetworkCache = new ServerSignalNetworkCache();
        serverSignalNetworkCache.flushAndApplyNewSignalStates(
                ISignalNetwork.deserializeSignalStatesFromNBTList(compoundTag)
        );
        return serverSignalNetworkCache;
    }

    private static ServerSignalNetworkCache create() {
        return new ServerSignalNetworkCache();
    }


    private void removeDeadEdgePointReferences() {
        List<UUID> pointsToRemove = new ArrayList<>();
        for (UUID uuidSignalStateNodeEntry : signalStateMapping.keySet()) {
            boolean doesNotExist = true;
            for (TrackGraph graph : Create.RAILWAYS.trackNetworks.values()) {
                for (EdgePointType<?> pointTypes : EdgePointType.TYPES.values()) {
                    if (graph.getPoint(pointTypes, uuidSignalStateNodeEntry) != null) {
                        doesNotExist = false;
                        break;
                    }
                }
            }

            if (doesNotExist) {
                pointsToRemove.add(uuidSignalStateNodeEntry);
                ExtendedSignals.LOGGER.info("Cleaned up dead reference {}", uuidSignalStateNodeEntry);
            }
        }

        pointsToRemove.forEach(signalStateMapping::remove);
    }

    /**
     * @return
     */
    @Override
    public Map<UUID, Couple<SignalStateNode>> signalStateMapping() {
        return this.signalStateMapping;
    }

    /**
     * @param tag
     * @param registries
     * @return
     */
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        this.removeDeadEdgePointReferences();

        final ListTag signalsCollectionTag = ISignalNetwork.serializeSignalStatesToNBTList(signalStateMapping());
        tag.put(ISignalNetwork.TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME, signalsCollectionTag);
        return tag;
    }

    /**
     * @param id
     * @param direction
     * @return
     */
    @Override
    public SignalStateNode prepareModification(UUID id, boolean direction) {
        SignalStateNode state = ISignalNetwork.super.prepareModification(id, direction);
        this.setDirty(true);

        return state;
    }

    public void synchronize(UUID id, boolean direction) {
        if (id == null)
            return;
        // TODO: Hash-based network state comparison and synchronization
        SignalStateNode updatedState = getSignalState(id, direction);
        if (signalStateNetworkHashes.computeIfAbsent(id, uuid -> Couple.create(0, 0)).get(direction) == updatedState.networkStateHash()) {
            return;
        }

        queuedUpdates.offer(new SignalSyncEntry(id, direction, updatedState));
        signalStateNetworkHashes.get(id).set(direction, updatedState.networkStateHash());
    }

    public void synchronize(SignalStateNode state) {
        if (state == null)
            return;
        synchronize(state.getThisStateID(), state.isThisStateFront());
    }

    public void tickSynchronize() {
        // while (!queuedUpdates.isEmpty()) {
        //     SignalSyncEntry pair = queuedUpdates.remove();
        //     SignalStateNode state = getSignalState(pair.id(), pair.direction());
        //     if (state.networkStateHash() == pair.networkStateHash())
        //         return;
        //     PacketDistributor.sendToAllPlayers(new ClientBoundSyncSignalStatePacket(pair.id(),  pair.direction(), state));
        // }
        // TODO: Make so that signals are only updated as needed
        // for (boolean b : Iterate.trueAndFalse) {
        //     signalStateMapping().forEach((uuid, signalState) -> {
        //         PacketDistributor.sendToAllPlayers(new ClientBoundSyncSignalStatePacket(uuid, b, signalState.get(b)));
        //     });
        // }
        while (!queuedUpdates.isEmpty()) {
            SignalSyncEntry entry = queuedUpdates.remove();
            PacketDistributor.sendToAllPlayers(new ClientBoundSyncSignalStatePacket(entry.id(), entry.direction(), entry.newState()));
        }
    }

    private record SignalSyncEntry(UUID id, boolean direction, SignalStateNode newState) {
    }
}
