package venomized.mods.extendedsignals.core.network.packets;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Map;
import java.util.UUID;

public record ClientBoundSyncSignalStatesPacket(
        Map<UUID, Couple<SignalStateNode>> fullNetworkMapping) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientBoundSyncSignalStatesPacket> TYPE =
            new Type<>(ExtendedSignals.res(ClientBoundSyncSignalStatesPacket.class.getSimpleName().toLowerCase()));

    public static final StreamCodec<FriendlyByteBuf, ClientBoundSyncSignalStatesPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    Object2ObjectArrayMap::new,
                    UUIDUtil.STREAM_CODEC,
                    Couple.streamCodec(SignalStateNode.STREAM_CODEC)
            ),
            ClientBoundSyncSignalStatesPacket::fullNetworkMapping,
            ClientBoundSyncSignalStatesPacket::new
    );

    public void handle(IPayloadContext context) {
        ExtendedSignals.clientNetworkCache().fromSync(fullNetworkMapping());
    }

    /**
     * @return
     */
    @Override
    public @NotNull Type<ClientBoundSyncSignalStatesPacket> type() {
        return TYPE;
    }
}
