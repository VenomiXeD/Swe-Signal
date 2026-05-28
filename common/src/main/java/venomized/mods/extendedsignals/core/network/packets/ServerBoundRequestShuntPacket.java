package venomized.mods.extendedsignals.core.network.packets;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.mixin_interfaces.ITrain;
import venomized.mods.extendedsignals.core.signalling.ShuntRequest;

import java.util.UUID;
import java.util.function.Supplier;

public record ServerBoundRequestShuntPacket(UUID trainUUID, boolean front,
                                            float shuntRequestDistance) implements ISimplePacket {
    public static ServerBoundRequestShuntPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundRequestShuntPacket(
                buf.readUUID(),
                buf.readBoolean(),
                buf.readFloat()
        );
    }

    /**
     * @param contextSupplier
     */
    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ITrain train = (ITrain) Create.RAILWAYS.trains.get(trainUUID);
            if (train == null)
                return;

            train.requestShunting(new ShuntRequest(contextSupplier.get().getSender(), front, shuntRequestDistance));
        });
        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(trainUUID);
        buf.writeBoolean(front);
        buf.writeFloat(shuntRequestDistance);
    }
}
