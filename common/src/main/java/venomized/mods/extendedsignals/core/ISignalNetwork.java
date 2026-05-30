package venomized.mods.extendedsignals.core;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Map;
import java.util.UUID;

public interface ISignalNetwork {
    String TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME = "signal_states";

    static ListTag serializeSignalStatesToNBTList(Map<UUID, Couple<SignalStateNode>> signalStates) {
        final ListTag signalsCollectionTag = new ListTag();
        signalStates.forEach((uuid, signal) -> {
            final CompoundTag entry = new CompoundTag();
            entry.putUUID("id", uuid);

            entry.put("data0", signal.getFirst().toNBT());
            entry.put("data1", signal.getSecond().toNBT());
            signalsCollectionTag.add(entry);
        });
        return signalsCollectionTag;
    }

    static Object2ObjectMap<UUID, Couple<SignalStateNode>> deserializeSignalStatesFromNBTList(final CompoundTag compoundTag) {
        Object2ObjectMap<UUID, Couple<SignalStateNode>> signalStates = new Object2ObjectOpenHashMap<>();

        compoundTag.getList(TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME, Tag.TAG_COMPOUND).forEach(listEntry -> {
            final CompoundTag entry = (CompoundTag) listEntry;
            signalStates.put(
                    entry.getUUID("id"),
                    Couple.create(
                            SignalStateNode.fromNBT(entry.getCompound("data0")),
                            SignalStateNode.fromNBT(entry.getCompound("data1"))
                    )
            );
        });

        return signalStates;
    }

    Object2ObjectMap<UUID, Couple<SignalStateNode>> signalStates();

    default void flushAndApplyNewSignalStates(Map<UUID, Couple<SignalStateNode>> newSignalNetwork) {
        this.signalStates().clear();
        this.signalStates().putAll(newSignalNetwork);
    }

    default void updateState(UUID id, boolean direction, SignalStateNode newState) {
        signalStates()
                .computeIfAbsent(id, uuid -> Couple.create(() -> SignalStateNode.INVALID))
                .set(direction, newState);
    }


    default SignalStateNode getSignalState(UUID id, boolean side) {
        return signalStates()
                .computeIfAbsent(id, uuid -> Couple.create(() -> SignalStateNode.INVALID))
                .get(side);
    }
}
