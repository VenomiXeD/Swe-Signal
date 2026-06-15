package venomized.mods.extendedsignals.core.network.packets;

import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;

public record ServerBoundModelConfigurePacket(BlockPos pos, Vec3 loc, Vec3 glo,
                                              Vec3 orientation) implements CustomPacketPayload {
    public static final Type<ServerBoundModelConfigurePacket> TYPE =
            new Type<>(ExtendedSignals.res(ServerBoundModelConfigurePacket.class.getSimpleName().toLowerCase()));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, ServerBoundModelConfigurePacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerBoundModelConfigurePacket::pos,
            CatnipStreamCodecs.VEC3,
            ServerBoundModelConfigurePacket::loc,
            CatnipStreamCodecs.VEC3,
            ServerBoundModelConfigurePacket::glo,
            CatnipStreamCodecs.VEC3,
            ServerBoundModelConfigurePacket::orientation,
            ServerBoundModelConfigurePacket::new
    );

    /**
     * @return
     */
    @Override
    public @NotNull Type<ServerBoundModelConfigurePacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext iPayloadContext) {
        iPayloadContext.enqueueWork(() -> {
            BlockEntity blockEntity = iPayloadContext.player().level()
                    .getBlockEntity(pos);

            if (blockEntity instanceof IConfigurableModelBlockEntity configurableModel) {
                configurableModel.setLocOffset(this.loc);
                configurableModel.setGblOffset(this.glo);
                configurableModel.setOrientation(this.orientation);
            }
        });
    }
}
