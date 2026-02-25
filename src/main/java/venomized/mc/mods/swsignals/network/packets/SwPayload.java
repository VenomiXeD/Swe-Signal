package venomized.mc.mods.swsignals.network.packets;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface SwPayload extends CustomPacketPayload {
    void handle(IPayloadContext context);
}
