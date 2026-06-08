package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import venomized.mods.extendedsignals.core.ExtendedSignals;

import java.util.function.Supplier;

public record UpdateATCEventPacket(double atcLimit) implements CustomPacketPayload {
    public static final Type<UpdateATCEventPacket> TYPE =
            new Type<>(ExtendedSignals.res(UpdateATCEventPacket.class.getSimpleName().toLowerCase()));

    public static UpdateATCEventPacket decode(FriendlyByteBuf buf) {
        return new UpdateATCEventPacket(buf.readDouble());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(atcLimit);
    }

    // public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
    //     contextSupplier.get().enqueueWork(() -> {
    //         //ATCOverlayHUD.setATCLimit(this.atcLimit);
    //         Minecraft.getInstance().player.level().playLocalSound(
    //                 Minecraft.getInstance().player.blockPosition(),
    //                 SoundEvents.NOTE_BLOCK_XYLOPHONE.get(),//AllSounds.SE_ATC_TONE.get(),
    //                 SoundSource.NEUTRAL,
    //                 1, 1, false
    //         );
    //     });
    //     contextSupplier.get().setPacketHandled(true);
    // }

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
