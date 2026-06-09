package venomized.mods.extendedsignals.core.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.commons.lang3.EnumUtils;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;

import java.util.function.Supplier;

public record ServerBoundTranslateBlockPacket(BlockPos blockEntityPos,
                                              Direction direction) implements CustomPacketPayload {
    public static final Type<ServerBoundTranslateBlockPacket> TYPE =
            new Type<>(ExtendedSignals.res(ServerBoundTranslateBlockPacket.class.getSimpleName().toLowerCase()));

    public static final StreamCodec<FriendlyByteBuf, ServerBoundTranslateBlockPacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerBoundTranslateBlockPacket::blockEntityPos,
            Direction.STREAM_CODEC,
            ServerBoundTranslateBlockPacket::direction,
            ServerBoundTranslateBlockPacket::new
    );

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext iPayloadContext) {
        iPayloadContext.enqueueWork(() -> {
            BlockEntity blockEntity = iPayloadContext.player().level().getBlockEntity(blockEntityPos);
            if (!(blockEntity instanceof IConfigurableModelBlockEntity translatableBlock))
                return;
// //
            final double offsetValue = 1 / 16d;
            switch (direction) {
                case UP:
                    translatableBlock.setYGblOffset(translatableBlock.getYGblOffset() + offsetValue);
                    break;
                case DOWN:
                    translatableBlock.setYGblOffset(translatableBlock.getYGblOffset() - offsetValue);
                    break;
                case NORTH:
                    translatableBlock.setZGblOffset(translatableBlock.getZGblOffset() - offsetValue);
                    break;
                case SOUTH:
                    translatableBlock.setZGblOffset(translatableBlock.getZGblOffset() + offsetValue);
                    break;
                case WEST:
                    translatableBlock.setXGblOffset(translatableBlock.getXGblOffset() - offsetValue);
                    break;
                case EAST:
                    translatableBlock.setXGblOffset(translatableBlock.getXGblOffset() + offsetValue);
                    break;
            }
        });
    }
}
