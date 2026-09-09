package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.client.MiscTrainDataClientSync;

import java.util.UUID;

public record ClientBoundMiscTrainDataPacket(UUID trainID, double trainSpeed,
                                             double acceleration) implements CustomPacketPayload {
    public static Type<ClientBoundMiscTrainDataPacket> TYPE = new Type<>(ExtendedSignals.res(ClientBoundMiscTrainDataPacket.class.getSimpleName().toLowerCase()));

    public static final StreamCodec<FriendlyByteBuf, ClientBoundMiscTrainDataPacket> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientBoundMiscTrainDataPacket::trainID,
            ByteBufCodecs.DOUBLE,
            ClientBoundMiscTrainDataPacket::trainSpeed,
            ByteBufCodecs.DOUBLE,
            ClientBoundMiscTrainDataPacket::acceleration,
            ClientBoundMiscTrainDataPacket::new
    );

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        MiscTrainDataClientSync.updateTrainData(trainID, new MiscTrainDataClientSync.TrainData(trainSpeed, acceleration));
    }
}
