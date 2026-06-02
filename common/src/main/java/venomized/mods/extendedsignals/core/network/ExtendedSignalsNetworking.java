package venomized.mods.extendedsignals.core.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.network.packets.*;

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
                ServerBoundScrollItemPacket.class,
                ServerBoundScrollItemPacket::encode,
                ServerBoundScrollItemPacket::decode,
                ServerBoundScrollItemPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                UpdateATCEventPacket.class,
                UpdateATCEventPacket::encode,
                UpdateATCEventPacket::decode,
                UpdateATCEventPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                ClientBoundSyncSignalStatesPacket.class,
                ClientBoundSyncSignalStatesPacket::encode,
                ClientBoundSyncSignalStatesPacket::decode,
                ClientBoundSyncSignalStatesPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                ClientBoundSyncSignalStatePacket.class,
                ClientBoundSyncSignalStatePacket::encode,
                ClientBoundSyncSignalStatePacket::decode,
                ClientBoundSyncSignalStatePacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                ServerBoundRequestShuntPacket.class,
                ServerBoundRequestShuntPacket::encode,
                ServerBoundRequestShuntPacket::decode,
                ServerBoundRequestShuntPacket::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                ServerBoundTranslateBlockPacket.class,
                ServerBoundTranslateBlockPacket::encode,
                ServerBoundTranslateBlockPacket::decode,
                ServerBoundTranslateBlockPacket::handle
        );
    }
}
