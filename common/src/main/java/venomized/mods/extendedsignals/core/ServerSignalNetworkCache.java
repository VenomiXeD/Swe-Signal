package venomized.mods.extendedsignals.core;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.SyncSignalStatePacket;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.UUID;

/**
 * A class that represents the signal network for both the client and server.
 */
public class ServerSignalNetworkCache extends SavedData implements ISignalNetwork {
    private static final String NAME = "extended_signal_signal_mapping_data";

    private final Object2ObjectMap<UUID, SignalStateNode> signalEdgeStateMapping = new Object2ObjectOpenHashMap<>();

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
        test.signalStates().put(UUID.randomUUID(), new SignalStateNode().setProceed(true));
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
    private void removeInvalidSignalEdgeReferences() {

    }

    /**
     * @return
     */
    @Override
    public Object2ObjectMap<UUID, SignalStateNode> signalStates() {
        return this.signalEdgeStateMapping;
    }

    /**
     * @param pCompoundTag the {@code CompoundTag} to save the {@code SavedData} to
     * @return
     */
    @Override
    public @NotNull CompoundTag save(final CompoundTag pCompoundTag) {
        final ListTag signalsCollectionTag = ISignalNetwork.serializeSignalStatesToNBTList(signalStates());
        pCompoundTag.put(ISignalNetwork.TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME, signalsCollectionTag);
        return pCompoundTag;
    }

    /**
     * @param signalUUID
     * @param newState
     */
    @Override
    public void updateState(UUID signalUUID, SignalStateNode newState) {
        if (newState.equals(this.signalEdgeStateMapping.getOrDefault(signalUUID, null)))
            return;
        ISignalNetwork.super.updateState(signalUUID, newState);


        ExtendedSignalsNetworking.CHANNEL.send(
                PacketDistributor.ALL.noArg(),
                new SyncSignalStatePacket(signalUUID, newState)
        );

        this.setDirty(true);
    }
}
