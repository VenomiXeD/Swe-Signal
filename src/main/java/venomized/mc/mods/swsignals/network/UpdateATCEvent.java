package venomized.mc.mods.swsignals.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import venomized.mc.mods.swsignals.client.ui.overlays.ATCOverlayHUD;

import java.util.function.Supplier;

public class UpdateATCEvent {
    public double atcLimit;

    public UpdateATCEvent(double atcLimit) {
        this.atcLimit = atcLimit;
    }

    public static UpdateATCEvent decode(FriendlyByteBuf buf) {
        return new UpdateATCEvent(buf.readDouble());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(atcLimit);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ATCOverlayHUD.setATCLimit(this.atcLimit);
        contextSupplier.get().setPacketHandled(true);
    }
}
