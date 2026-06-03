package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;

import java.util.function.Supplier;

public record ServerBoundTranslateBlockPacket(BlockPos blockEntityPos, Direction direction) implements ISimplePacket {
    public static ServerBoundTranslateBlockPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundTranslateBlockPacket(
                buf.readBlockPos(),
                buf.readEnum(Direction.class)
        );
    }

    /**
     * @param contextSupplier
     */
    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            BlockEntity blockEntity = contextSupplier.get().getSender().level().getBlockEntity(blockEntityPos);
            if (!(blockEntity instanceof IConfigurableModelBlockEntity translatableBlock))
                return;

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

        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockEntityPos);
        buf.writeEnum(direction);
    }
}
