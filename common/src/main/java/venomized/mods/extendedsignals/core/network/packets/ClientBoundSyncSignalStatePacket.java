package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.ISignalNetwork;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.UUID;
import java.util.function.Supplier;

public record ClientBoundSyncSignalStatePacket(UUID uuid, boolean side,
                                               SignalStateNode signalStateNode) implements ISimplePacket {
    public static ClientBoundSyncSignalStatePacket decode(FriendlyByteBuf buf) {
        return new ClientBoundSyncSignalStatePacket(
                buf.readUUID(),
                buf.readBoolean(),
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
        ExtendedSignalsCore.clientNetworkCache().updateState(this.uuid, side, this.signalStateNode);
        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeBoolean(side);
        buf.writeNbt(signalStateNode.toNBT());
    }
}
