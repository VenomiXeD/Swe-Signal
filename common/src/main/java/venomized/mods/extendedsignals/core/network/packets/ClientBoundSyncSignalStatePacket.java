package venomized.mods.extendedsignals.core.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.ExtendedSignalsClient;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.UUID;
import java.util.function.Supplier;

public record ClientBoundSyncSignalStatePacket(UUID uuid, boolean side,
                                               SignalStateNode signalStateNode) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientBoundSyncSignalStatePacket> TYPE = new CustomPacketPayload.Type<>(
            ExtendedSignals.res(ClientBoundSyncSignalStatePacket.class.getSimpleName().toLowerCase()));

    public static final StreamCodec<FriendlyByteBuf, ClientBoundSyncSignalStatePacket> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientBoundSyncSignalStatePacket::uuid,
            ByteBufCodecs.BOOL,
            ClientBoundSyncSignalStatePacket::side,
            SignalStateNode.STREAM_CODEC,
            ClientBoundSyncSignalStatePacket::signalStateNode,
            ClientBoundSyncSignalStatePacket::new
    );

    // /**
    //  * @param contextSupplier
    //  */
    // @Override
    // public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
    //     // ExtendedSignalsCore.LOGGER
    //     //         .info("new signal state update: {}, {}", uuid, NbtUtils.prettyPrint(this.signalStateNode().toNBT()));
    //     ExtendedSignals.clientNetworkCache().updateState(uuid, side, signalStateNode);
    //     contextSupplier.get().setPacketHandled(true);
    // }
    /**
     * @return
     */
    @Override
    public Type<ClientBoundSyncSignalStatePacket> type() {
        return TYPE;
    }

    public static void handle(ClientBoundSyncSignalStatePacket packet, IPayloadContext iPayloadContext) {
        ExtendedSignals.clientNetworkCache().updateState(
                packet.uuid(),
                packet.side(),
                packet.signalStateNode()
        );
    }
}
