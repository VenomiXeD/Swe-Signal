package venomized.mc.mods.swsignals.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import venomized.mc.mods.swsignals.item.IScrollableItem;

import java.util.function.Supplier;

/**
 * CLIENT -> SERVER
 * Packet for handling when client scrolled
 */
public class ClientScrollNetworkEvent {
    private final boolean up;

    public ClientScrollNetworkEvent(boolean up) {
        this.up = up;
    }

    public ClientScrollNetworkEvent(FriendlyByteBuf buf) {
        this.up = buf.readBoolean();
    }

    public static void handle(ClientScrollNetworkEvent event, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ItemStack mainHandItem = ctx.get().getSender().getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableItem) {
                scrollableItem.onItemScroll(ctx.get().getSender(), mainHandItem, event.up);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(up);
    }
}
