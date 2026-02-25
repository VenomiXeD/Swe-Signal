package venomized.mc.mods.swsignals.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mc.mods.swsignals.core.SwSignal;
import venomized.mc.mods.swsignals.item.IScrollableItem;

/**
 * CLIENT -> SERVER
 * Packet for handling when client scrolled
 */
public record ClientScrollNetworkEventPacket(boolean up) implements SwPayload {

    public static final Type<ClientScrollNetworkEventPacket> TYPE = new Type<>(SwSignal.resource("client_scroll"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientScrollNetworkEventPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ClientScrollNetworkEventPacket::up,
            ClientScrollNetworkEventPacket::new
    );

    public static ClientScrollNetworkEventPacket decode(FriendlyByteBuf buf) {
        return new ClientScrollNetworkEventPacket(buf.readBoolean());
    }

    @Override
    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ItemStack mainHandItem = ctx.player().getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableItem) {
                scrollableItem.onItemScroll(ctx.player(), mainHandItem, up);
            }
        });
    }

    @Override
    public Type<ClientScrollNetworkEventPacket> type() {
        return TYPE;
    }
}
