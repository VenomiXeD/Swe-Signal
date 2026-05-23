package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public record SyncSignalStatesPacket(Map<UUID, RawSignalState> fullNetworkMapping) implements ISimplePacket {
    public static SyncSignalStatesPacket decode(FriendlyByteBuf buf) {
        return new SyncSignalStatesPacket(
                ISignalNetwork.deserializeSignalStatesFromNBTList(
                        Objects.requireNonNull(buf.readAnySizeNbt())
                ));
    }

    /**
     * @param contextSupplier
     */
    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ExtendedSignalsCore.clientNetworkCache().fromSync(fullNetworkMapping());
        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        final CompoundTag tag = new CompoundTag();
        tag.put(ISignalNetwork.TAG_SIGNAL_STATE_NBT_LIST_COLLECTION_NAME, ISignalNetwork.serializeSignalStatesToNBTList(fullNetworkMapping()));
        buf.writeNbt(tag);
    }
}
