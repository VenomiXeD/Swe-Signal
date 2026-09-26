package venomized.mods.extendedsignals.core.signalling;

import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.createmod.catnip.data.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

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

    static Map<UUID, Couple<SignalStateNode>> deserializeSignalStatesFromNBTList(final CompoundTag compoundTag) {
        Map<UUID, Couple<SignalStateNode>> signalStates = new Object2ReferenceOpenHashMap<>();

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

    Map<UUID, Couple<SignalStateNode>> signalStateMapping();


    default void flushAndApplyNewSignalStates(Map<UUID, Couple<SignalStateNode>> newSignalNetwork) {
        this.signalStateMapping().clear();
        this.signalStateMapping().putAll(newSignalNetwork);
    }

    @Deprecated
    default SignalStateNode updateState(UUID id, boolean direction, SignalStateNode newState) {
        return modifyState(id, direction, s -> s.copyValues(newState));
    }

    default SignalStateNode modifyState(UUID id, boolean direction, Consumer<SignalStateNode> changeSignalStateCallback) {
        if (id == null)
            return null;

        SignalStateNode state = getSignalState(id, direction);
        state.setThisStateID(id);
        state.setThisStateFront(direction);
        changeSignalStateCallback.accept(state);

        return state;
    }


    default SignalStateNode getSignalState(UUID id, boolean side) {
        if (id == null)
            return SignalStateNode.INVALID((UUID) null, side);

        return signalStateMapping().computeIfAbsent(id, ISignalNetwork::emptySignalStateNode).get(side);
    }

    static Couple<SignalStateNode> emptySignalStateNode(UUID id) {
        return Couple.createWithContext(s -> new SignalStateNode(id, s));
    }
}
