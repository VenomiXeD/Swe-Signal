package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;

import java.util.function.Supplier;

public record ServerBoundTranslateBlockPacket(BlockPos blockEntityPos,
                                              Direction direction) implements CustomPacketPayload {
    public static final Type<ServerBoundTranslateBlockPacket> TYPE =
            new Type<>(ExtendedSignals.res(ServerBoundTranslateBlockPacket.class.getSimpleName().toLowerCase()));
    public static ServerBoundTranslateBlockPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundTranslateBlockPacket(
                buf.readBlockPos(),
                buf.readEnum(Direction.class)
        );
    }

    // /**
    //  * @param contextSupplier
    //  */
    // @Override
    // public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
    //     // contextSupplier.get().enqueueWork(() -> {
    //     //     BlockEntity blockEntity = contextSupplier.get().getSender().level().getBlockEntity(blockEntityPos);
    //     //     if (!(blockEntity instanceof IConfigurableModelBlockEntity translatableBlock))
    //     //         return;
// //
    //     //     final double offsetValue = 1 / 16d;
    //     //     switch (direction) {
    //     //         case UP:
    //     //             translatableBlock.setYGblOffset(translatableBlock.getYGblOffset() + offsetValue);
    //     //             break;
    //     //         case DOWN:
    //     //             translatableBlock.setYGblOffset(translatableBlock.getYGblOffset() - offsetValue);
    //     //             break;
    //     //         case NORTH:
    //     //             translatableBlock.setZGblOffset(translatableBlock.getZGblOffset() - offsetValue);
    //     //             break;
    //     //         case SOUTH:
    //     //             translatableBlock.setZGblOffset(translatableBlock.getZGblOffset() + offsetValue);
    //     //             break;
    //     //         case WEST:
    //     //             translatableBlock.setXGblOffset(translatableBlock.getXGblOffset() - offsetValue);
    //     //             break;
    //     //         case EAST:
    //     //             translatableBlock.setXGblOffset(translatableBlock.getXGblOffset() + offsetValue);
    //     //             break;
    //     //     }
    //     // });
    // }

    // /**
    //  * @param buf
    //  */
    // @Override
    // public void encode(FriendlyByteBuf buf) {
    //     buf.writeBlockPos(this.blockEntityPos);
    //     buf.writeEnum(direction);
    // }

    /**
     * @return
     */
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
