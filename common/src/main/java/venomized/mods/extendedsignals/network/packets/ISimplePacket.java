package venomized.mods.extendedsignals.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ISimplePacket {
    void handle(Supplier<NetworkEvent.Context> contextSupplier);

    void encode(FriendlyByteBuf buf);
}
