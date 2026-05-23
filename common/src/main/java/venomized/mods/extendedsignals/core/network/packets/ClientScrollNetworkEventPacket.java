package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.item.IScrollableItem;

import java.util.function.Supplier;

/**
 * CLIENT -> SERVER
 * Packet for handling when client scrolled
 */
public record ClientScrollNetworkEventPacket(boolean up) implements ISimplePacket {
    public static ClientScrollNetworkEventPacket decode(FriendlyByteBuf buf) {
        return new ClientScrollNetworkEventPacket(buf.readBoolean());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ItemStack mainHandItem = ctx.get().getSender().getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableItem) {
                scrollableItem.onItemScroll(ctx.get().getSender(), mainHandItem, up);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(up);
    }
}
