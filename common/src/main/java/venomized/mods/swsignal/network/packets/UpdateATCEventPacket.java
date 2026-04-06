package venomized.mods.swsignal.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.client.ui.overlays.ATCOverlayHUD;

import java.util.function.Supplier;

public record UpdateATCEventPacket(double atcLimit) {
    public static UpdateATCEventPacket decode(FriendlyByteBuf buf) {
        return new UpdateATCEventPacket(buf.readDouble());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(atcLimit);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ATCOverlayHUD.setATCLimit(this.atcLimit);
            Minecraft.getInstance().player.level().playLocalSound(
                    Minecraft.getInstance().player.blockPosition(),
                    AllSounds.SE_ATC_TONE.get(),
                    SoundSource.NEUTRAL,
                    1, 1, false
            );
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
