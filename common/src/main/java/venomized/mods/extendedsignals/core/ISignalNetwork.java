package venomized.mods.extendedsignals.core;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Map;
import java.util.UUID;

public interface ISignalNetwork {
    String TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME = "signal_states";

    static ListTag serializeSignalStatesToNBTList(Map<UUID, SignalStateNode> signalStates) {
        final ListTag signalsCollectionTag = new ListTag();
        signalStates.forEach((uuid, signal) -> {
            final CompoundTag entry = new CompoundTag();
            entry.putUUID("id", uuid);
            entry.put("data", signal.toNBT());
            signalsCollectionTag.add(entry);
        });

        return signalsCollectionTag;
    }

    static Object2ObjectMap<UUID, SignalStateNode> deserializeSignalStatesFromNBTList(final CompoundTag compoundTag) {
        Object2ObjectMap<UUID, SignalStateNode> signalStates = new Object2ObjectOpenHashMap<>();

        compoundTag.getList(TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME, Tag.TAG_COMPOUND).forEach(listEntry -> {
            final CompoundTag entry = (CompoundTag) listEntry;
            signalStates.put(entry.getUUID("id"), SignalStateNode.fromNBT(entry.getCompound("data")));
        });

        return signalStates;
    }

    Object2ObjectMap<UUID, SignalStateNode> signalStates();

    default void flushAndApplyNewSignalStates(Map<UUID, SignalStateNode> newSignalNetwork) {
        this.signalStates().clear();
        this.signalStates().putAll(newSignalNetwork);
    }

    default void updateState(UUID signalUUID, SignalStateNode newState) {
        signalStates()
                .put(signalUUID, newState);
    }


    default SignalStateNode getSignalState(UUID id) {
        return signalStates().getOrDefault(id, SignalStateNode.INVALID);
    }
}
