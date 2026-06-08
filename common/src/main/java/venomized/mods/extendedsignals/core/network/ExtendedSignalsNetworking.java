package venomized.mods.extendedsignals.core.network;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.network.packets.*;

public class ExtendedSignalsNetworking {
    private static final String NET_VERSION = "1.0";
    public static PayloadRegistrar CHANNEL;
    private static final int MSG_ID = 0;

    public static void init() {
        // CHANNEL.registerMessage(MSG_ID++,
        //         ServerBoundScrollItemPacket.class,
        //         ServerBoundScrollItemPacket::encode,
        //         ServerBoundScrollItemPacket::decode,
        //         ServerBoundScrollItemPacket::handle
        // );
//
        // CHANNEL.registerMessage(MSG_ID++,
        //         UpdateATCEventPacket.class,
        //         UpdateATCEventPacket::encode,
        //         UpdateATCEventPacket::decode,
        //         UpdateATCEventPacket::handle
        // );
//
        // CHANNEL.registerMessage(MSG_ID++,
        //         ClientBoundSyncSignalStatesPacket.class,
        //         ClientBoundSyncSignalStatesPacket::encode,
        //         ClientBoundSyncSignalStatesPacket::decode,
        //         ClientBoundSyncSignalStatesPacket::handle
        // );
//
        // CHANNEL.registerMessage(MSG_ID++,
        //         ClientBoundSyncSignalStatePacket.class,
        //         ClientBoundSyncSignalStatePacket::encode,
        //         ClientBoundSyncSignalStatePacket::decode,
        //         ClientBoundSyncSignalStatePacket::handle
        // );
//
        // CHANNEL.registerMessage(MSG_ID++,
        //         ServerBoundRequestShuntPacket.class,
        //         ServerBoundRequestShuntPacket::encode,
        //         ServerBoundRequestShuntPacket::decode,
        //         ServerBoundRequestShuntPacket::handle
        // );
//
        // CHANNEL.registerMessage(MSG_ID++,
        //         ServerBoundTranslateBlockPacket.class,
        //         ServerBoundTranslateBlockPacket::encode,
        //         ServerBoundTranslateBlockPacket::decode,
        //         ServerBoundTranslateBlockPacket::handle
        // );
//
        // CHANNEL.registerMessage(MSG_ID++,
        //         ServerBoundModelConfigurePacket.class,
        //         ServerBoundModelConfigurePacket::encode,
        //         ServerBoundModelConfigurePacket::decode,
        //         ServerBoundModelConfigurePacket::handle
        // );
    }

    @SubscribeEvent
    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        CHANNEL = event.registrar(NET_VERSION);

        CHANNEL.playToClient(
                ClientBoundSyncSignalStatePacket.TYPE,
                ClientBoundSyncSignalStatePacket.CODEC,
                ClientBoundSyncSignalStatePacket::handle
        );

        CHANNEL.playToClient(
                ClientBoundSyncSignalStatesPacket.TYPE,
                ClientBoundSyncSignalStatesPacket.CODEC,
                ClientBoundSyncSignalStatesPacket::handle
        );

        CHANNEL.playToServer(
                ServerBoundScrollItemPacket.TYPE,
                ServerBoundScrollItemPacket.CODEC,
                ServerBoundScrollItemPacket::handle
        );
    }
}
