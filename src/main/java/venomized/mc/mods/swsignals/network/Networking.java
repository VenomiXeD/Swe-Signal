package venomized.mc.mods.swsignals.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import venomized.mc.mods.swsignals.network.packets.ClientScrollNetworkEventPacket;
import venomized.mc.mods.swsignals.network.packets.UpdateATCEventPacket;

public class Networking {
    private static final String NET_VERSION = "1.0";

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar(NET_VERSION)
                .playToServer(ClientScrollNetworkEventPacket.TYPE, ClientScrollNetworkEventPacket.STREAM_CODEC, ClientScrollNetworkEventPacket::handle)
                .playToClient(UpdateATCEventPacket.TYPE, UpdateATCEventPacket.STREAM_CODEC, UpdateATCEventPacket::handle);
    }
}
