package venomized.mods.extendedsignals.core.network.packets;

import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.client.MiscTrainDataClientSync;
import venomized.mods.extendedsignals.core.create.SignalPolicy;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;

import java.util.Locale;
import java.util.UUID;

public record ClientBoundMiscTrainDataPacket(UUID trainID, double trainSpeed,
                                             double acceleration,
                                             SignalPolicy signalPolicy) implements CustomPacketPayload {
    public static Type<ClientBoundMiscTrainDataPacket> TYPE = new Type<>(ExtendedSignals.res(ClientBoundMiscTrainDataPacket.class.getSimpleName().toLowerCase(Locale.ROOT)));

    public static final StreamCodec<FriendlyByteBuf, ClientBoundMiscTrainDataPacket> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ClientBoundMiscTrainDataPacket::trainID,
            ByteBufCodecs.DOUBLE,
            ClientBoundMiscTrainDataPacket::trainSpeed,
            ByteBufCodecs.DOUBLE,
            ClientBoundMiscTrainDataPacket::acceleration,
            SignalPolicy.STREAM_CODEC,
            ClientBoundMiscTrainDataPacket::signalPolicy,
            ClientBoundMiscTrainDataPacket::new
    );

    public static void broadcastTrainUpdate(Train trainInstance, boolean stopped) {
        if (trainInstance == null)
            return;

        ITrain itrain = ITrain.cast(trainInstance);
        PacketDistributor.sendToAllPlayers(
                new ClientBoundMiscTrainDataPacket(
                        trainInstance.id,
                        stopped ? 0 : trainInstance.speed * 20f,
                        stopped ? 0 : (itrain.extendedSignals$trackedSpeed().fromValue() - itrain.extendedSignals$trackedSpeed().toValue()) * 20f,
                        itrain.advancedSignalScout().getSignalPolicyMode()
                )
        );
    }

    /**
     * @return
     */
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        MiscTrainDataClientSync.updateTrainData(trainID, new MiscTrainDataClientSync.TrainData(trainSpeed, acceleration, signalPolicy));
    }
}
