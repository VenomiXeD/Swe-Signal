package venomized.mods.extendedsignals.core.network.packets;

import com.simibubi.create.CreateClient;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.graph.TrackGraph;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignal;

import java.util.Locale;
import java.util.UUID;

public record ClientBoundUpdateMainSignalEdgePointTagPacket(ResourceLocation edgePointTypeId, UUID point, boolean front,
                                                            boolean value) implements CustomPacketPayload {
    public static final Type<ClientBoundUpdateMainSignalEdgePointTagPacket> TYPE = new Type<>(ExtendedSignals.res(ClientBoundUpdateMainSignalEdgePointTagPacket.class.getSimpleName().toLowerCase(Locale.ROOT)));

    public static StreamCodec<FriendlyByteBuf, ClientBoundUpdateMainSignalEdgePointTagPacket> CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC,
            ClientBoundUpdateMainSignalEdgePointTagPacket::edgePointTypeId,
            UUIDUtil.STREAM_CODEC,
            ClientBoundUpdateMainSignalEdgePointTagPacket::point,
            ByteBufCodecs.BOOL,
            ClientBoundUpdateMainSignalEdgePointTagPacket::front,
            ByteBufCodecs.BOOL,
            ClientBoundUpdateMainSignalEdgePointTagPacket::value,
            ClientBoundUpdateMainSignalEdgePointTagPacket::new
    );

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(final IPayloadContext context) {
        for (TrackGraph trackGraph : CreateClient.RAILWAYS.trackNetworks.values()) {
            if (trackGraph.getPoint(EdgePointType.TYPES.get(edgePointTypeId), point) instanceof ISignal<?> signalPoint) {
                signalPoint.setMainSignal(front, value);
                return;
            }
        }
    }
}
