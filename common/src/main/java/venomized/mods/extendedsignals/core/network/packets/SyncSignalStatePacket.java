package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import java.util.UUID;
import java.util.function.Supplier;

public record SyncSignalStatePacket(UUID uuid, RawSignalState rawSignalState) implements ISimplePacket {
    public static SyncSignalStatePacket decode(FriendlyByteBuf buf) {
        return new SyncSignalStatePacket(
                buf.readUUID(),
                RawSignalState.fromNBT(buf.readAnySizeNbt())
        );
    }

    /**
     * @param contextSupplier
     */
    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ExtendedSignalsCore.LOGGER
                .info("new signal state update: {}, {}", uuid, NbtUtils.prettyPrint(this.rawSignalState().toNBT()));
        ExtendedSignalsCore.clientNetworkCache()
                .updateState(this.uuid, this.rawSignalState);
        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.uuid);
        buf.writeNbt(this.rawSignalState.toNBT());
    }
}
