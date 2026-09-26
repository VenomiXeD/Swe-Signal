package venomized.mods.extendedsignals.core;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.graph.TrackGraph;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundSyncSignalStatePacket;
import venomized.mods.extendedsignals.core.signalling.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.*;
import java.util.function.Consumer;

/**
 * A class that represents the signal network for both the client and server.
 */
public class ServerSignalNetworkCache extends SavedData implements ISignalNetwork {
    private static final String NAME = "extended_signals_signal_mapping_data";

    private final Map<UUID, Couple<SignalStateNode>> signalEdgeStateMapping = new Object2ReferenceOpenHashMap<>();

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
        for (UUID uuidSignalStateNodeEntry : signalEdgeStateMapping.keySet()) {
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

        pointsToRemove.forEach(signalEdgeStateMapping::remove);
    }

    /**
     * @return
     */
    @Override
    public Map<UUID, Couple<SignalStateNode>> signalStateMapping() {
        return this.signalEdgeStateMapping;
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
     * @param changeSignalStateCallback
     * @return
     */
    @Override
    public SignalStateNode modifyState(UUID id, boolean direction, Consumer<SignalStateNode> changeSignalStateCallback) {
        int old = getSignalState(id, direction).networkStateHash();
        SignalStateNode state = ISignalNetwork.super.modifyState(id, direction, changeSignalStateCallback);
        if (old == state.networkStateHash())
            return state;

        PacketDistributor.sendToAllPlayers(new ClientBoundSyncSignalStatePacket(id, direction, state));
        this.setDirty(true);

        return state;
    }
}
