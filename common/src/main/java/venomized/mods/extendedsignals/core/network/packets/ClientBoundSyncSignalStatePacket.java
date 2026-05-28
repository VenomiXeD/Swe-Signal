package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.UUID;
import java.util.function.Supplier;

public record ClientBoundSyncSignalStatePacket(UUID uuid, SignalStateNode signalStateNode) implements ISimplePacket {
    public static ClientBoundSyncSignalStatePacket decode(FriendlyByteBuf buf) {
        return new ClientBoundSyncSignalStatePacket(
                buf.readUUID(),
                SignalStateNode.fromNBT(buf.readAnySizeNbt())
        );
    }

    /**
     * @param contextSupplier
     */
    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        // ExtendedSignalsCore.LOGGER
        //         .info("new signal state update: {}, {}", uuid, NbtUtils.prettyPrint(this.signalStateNode().toNBT()));
        ExtendedSignalsCore.clientNetworkCache().updateState(this.uuid, this.signalStateNode);
        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.uuid);
        buf.writeNbt(this.signalStateNode.toNBT());
    }
}
