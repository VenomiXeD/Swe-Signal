package venomized.mods.extendedsignals.core.network.packets;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.createmod.catnip.data.Couple;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPointModifier;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public record ServerBoundConfigurePointPacket(BlockPos targetBlockEntityPos,
                                              CompoundTag properties) implements CustomPacketPayload {

    public static final Type<ServerBoundConfigurePointPacket> TYPE = new CustomPacketPayload.Type<>(
            ExtendedSignals.res(ServerBoundConfigurePointPacket.class.getSimpleName().toLowerCase())
    );


    public static final StreamCodec<FriendlyByteBuf, ServerBoundConfigurePointPacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerBoundConfigurePointPacket::targetBlockEntityPos,
            ByteBufCodecs.COMPOUND_TAG,
            ServerBoundConfigurePointPacket::properties,
            ServerBoundConfigurePointPacket::new
    );

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            BlockEntity blockEntity = ctx.player().level().getBlockEntity(targetBlockEntityPos());
            if (blockEntity instanceof BlockEntityPointModifier<?> modifier) {
                modifier.getProperties().fromNBT(properties);
                modifier.refreshPointProperties();
            }
        });
    }
}
