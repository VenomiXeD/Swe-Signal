package venomized.mods.extendedsignals.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.network.packets.*;

public class ExtendedSignalsNetworking {
    private static final String NET_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(ExtendedSignalsCore.MOD_ID, "main"),
            () -> NET_VERSION,
            NET_VERSION::equals,
            NET_VERSION::equals
    );
    private static int MSG_ID = 0;

    public static void init() {
        CHANNEL.registerMessage(MSG_ID++,
                ClientScrollNetworkEventPacket.class,
                ClientScrollNetworkEventPacket::encode,
                ClientScrollNetworkEventPacket::decode,
                ClientScrollNetworkEventPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                UpdateATCEventPacket.class,
                UpdateATCEventPacket::encode,
                UpdateATCEventPacket::decode,
                UpdateATCEventPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                SyncSignalStatesPacket.class,
                SyncSignalStatesPacket::encode,
                SyncSignalStatesPacket::decode,
                SyncSignalStatesPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                SyncSignalStatePacket.class,
                SyncSignalStatePacket::encode,
                SyncSignalStatePacket::decode,
                SyncSignalStatePacket::handle
        );
    }
}
