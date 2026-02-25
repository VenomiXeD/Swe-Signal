package venomized.mc.mods.swsignals.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mc.mods.swsignals.AllSounds;
import venomized.mc.mods.swsignals.client.ui.overlays.ATCOverlayHUD;
import venomized.mc.mods.swsignals.core.SwSignal;

public record UpdateATCEventPacket(double atcLimit) implements SwPayload {
    public static final Type<UpdateATCEventPacket> TYPE = new Type<>(SwSignal.resource("update_atc"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateATCEventPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, UpdateATCEventPacket::atcLimit,
            UpdateATCEventPacket::new
    );

    public static UpdateATCEventPacket decode(FriendlyByteBuf buf) {
        return new UpdateATCEventPacket(buf.readDouble());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(atcLimit);
    }

    @Override
    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ATCOverlayHUD.setATCLimit(this.atcLimit);
            Minecraft.getInstance().player.level().playLocalSound(
                    Minecraft.getInstance().player.blockPosition(),
                    AllSounds.SE_ATC_TONE.get(),
                    SoundSource.NEUTRAL,
                    1, 1, false
            );
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
