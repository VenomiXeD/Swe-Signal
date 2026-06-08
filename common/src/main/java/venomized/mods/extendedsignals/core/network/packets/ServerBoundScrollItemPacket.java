package venomized.mods.extendedsignals.core.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.item.IScrollableItem;

import java.util.function.Supplier;

/**
 * CLIENT -> SERVER
 * Packet for handling when client scrolled
 */
public record ServerBoundScrollItemPacket(boolean up) implements CustomPacketPayload {
    public static final Type<ServerBoundScrollItemPacket> TYPE =
            new Type<>(ExtendedSignals.res(ServerBoundScrollItemPacket.class.getSimpleName().toLowerCase()));
    public static final StreamCodec<FriendlyByteBuf, ServerBoundScrollItemPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ServerBoundScrollItemPacket::up,
            ServerBoundScrollItemPacket::new
    );

    public static ServerBoundScrollItemPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundScrollItemPacket(buf.readBoolean());
    }

    // public void handle(Supplier<NetworkEvent.Context> ctx) {
    //     ctx.get().enqueueWork(() -> {
    //         ItemStack mainHandItem = ctx.get().getSender().getMainHandItem();
    //         if (mainHandItem.getItem() instanceof IScrollableItem scrollableItem) {
    //             scrollableItem.onItemScroll(ctx.get().getSender(), mainHandItem, up);
    //         }
    //     });
    //     ctx.get().setPacketHandled(true);
    // }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(up);
    }

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext iPayloadContext) {
        iPayloadContext.enqueueWork(() -> {
            Item item = iPayloadContext.player().getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (item instanceof IScrollableItem) {
                ((IScrollableItem) item).onItemScroll(iPayloadContext.player(), iPayloadContext.player().getItemInHand(InteractionHand.MAIN_HAND), up());
            }
        });
    }
}
