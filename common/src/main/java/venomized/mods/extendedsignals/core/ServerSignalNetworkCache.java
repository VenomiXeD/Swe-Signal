package venomized.mods.extendedsignals.core;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.graph.TrackGraph;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.createmod.catnip.data.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.ClientBoundSyncSignalStatePacket;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class that represents the signal network for both the client and server.
 */
public class ServerSignalNetworkCache extends SavedData implements ISignalNetwork {
    private static final String NAME = "extended_signal_signal_mapping_data";

    private final Object2ObjectMap<UUID, Couple<SignalStateNode>> signalEdgeStateMapping = new Object2ObjectOpenHashMap<>();

    public static ServerSignalNetworkCache get(final MinecraftServer server) {
        ExtendedSignalsCore.LOGGER.info("Extended Signals is loading signal data...");
        ServerSignalNetworkCache data = server.overworld().getDataStorage().computeIfAbsent(
                ServerSignalNetworkCache::load,
                ServerSignalNetworkCache::create,
                NAME
        );

        ExtendedSignalsCore.EXTENDED_SIGNAL_CACHE_PROXY = data;

        return data;
    }

    private static ServerSignalNetworkCache create() {
        ServerSignalNetworkCache test = new ServerSignalNetworkCache();
        test.setDirty(true);
        return test;
    }

    private static ServerSignalNetworkCache load(final CompoundTag compoundTag) {
        ServerSignalNetworkCache serverSignalNetworkCache = new ServerSignalNetworkCache();
        serverSignalNetworkCache.flushAndApplyNewSignalStates(
                ISignalNetwork.deserializeSignalStatesFromNBTList(compoundTag)
        );
        return serverSignalNetworkCache;
    }

    // TODO: Remove signal states that are dead and do not refer to any signal edges in create
    private void removeInvalidSignalEdgePointReferences() {
        List<UUID> statesToRemove = new ArrayList<>();
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
                statesToRemove.add(uuidSignalStateNodeEntry);
                ExtendedSignalsCore.LOGGER.info("Cleaned up dead reference {}", uuidSignalStateNodeEntry);
            }
        }

        statesToRemove.forEach(signalEdgeStateMapping::remove);
    }

    /**
     * @return
     */
    @Override
    public Object2ObjectMap<UUID, Couple<SignalStateNode>> signalStates() {
        return this.signalEdgeStateMapping;
    }

    /**
     * @param pCompoundTag the {@code CompoundTag} to save the {@code SavedData} to
     * @return
     */
    @Override
    public @NotNull CompoundTag save(final CompoundTag pCompoundTag) {
        this.removeInvalidSignalEdgePointReferences();

        final ListTag signalsCollectionTag = ISignalNetwork.serializeSignalStatesToNBTList(signalStates());
        pCompoundTag.put(ISignalNetwork.TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME, signalsCollectionTag);
        return pCompoundTag;
    }

    /**
     * @param id
     * @param newState
     */
    @Override
    public void updateState(UUID id, boolean side, SignalStateNode newState) {
        if (newState.equals(this.signalEdgeStateMapping.getOrDefault(id, null)))
            return;
        ISignalNetwork.super.updateState(id, side, newState);


        ExtendedSignalsNetworking.CHANNEL.send(
                PacketDistributor.ALL.noArg(),
                new ClientBoundSyncSignalStatePacket(id, side, newState)
        );

        this.setDirty(true);
    }
}
