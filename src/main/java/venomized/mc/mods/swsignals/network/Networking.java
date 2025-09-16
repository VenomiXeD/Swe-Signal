package venomized.mc.mods.swsignals.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import venomized.mc.mods.swsignals.SwSignal;

public class Networking {
    private static final String NET_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(SwSignal.MOD_ID, "main"),
            () -> NET_VERSION,
            NET_VERSION::equals,
            NET_VERSION::equals
    );
    private static int MSG_ID = 0;

    public static void init() {
        CHANNEL.registerMessage(MSG_ID++,
                ClientScrollNetworkEvent.class,
                ClientScrollNetworkEvent::encode,
                ClientScrollNetworkEvent::new,
                ClientScrollNetworkEvent::handle
        );

        CHANNEL.registerMessage(MSG_ID++,
                UpdateATCEvent.class,
                UpdateATCEvent::encode,
                UpdateATCEvent::decode,
                UpdateATCEvent::handle
        );
    }
}
