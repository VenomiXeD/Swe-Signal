package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;

public record ClientBoundATCEventPacket(double atcLimit) implements CustomPacketPayload {
    public static final Type<ClientBoundATCEventPacket> TYPE =
            new Type<>(ExtendedSignals.res(ClientBoundATCEventPacket.class.getSimpleName().toLowerCase()));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientBoundATCEventPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            ClientBoundATCEventPacket::atcLimit,
            ClientBoundATCEventPacket::new
    );

    /**
     * @return
     */
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext iPayloadContext) {
        iPayloadContext.enqueueWork(() -> {
            //ATCOverlayHUD.setATCLimit(this.atcLimit);
            Minecraft.getInstance().player.level().playLocalSound(
                    Minecraft.getInstance().player.blockPosition(),
                    SoundEvents.NOTE_BLOCK_XYLOPHONE.value(),//AllSounds.SE_ATC_TONE.get(),
                    SoundSource.NEUTRAL,
                    1, 1, false
            );
        });
    }
}
