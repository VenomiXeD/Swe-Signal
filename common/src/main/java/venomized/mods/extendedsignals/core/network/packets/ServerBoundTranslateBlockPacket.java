package venomized.mods.extendedsignals.core.network.packets;

import com.simibubi.create.api.contraption.transformable.TransformableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.blockentity.ITranslatableBlockEntity;

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
            if (!(blockEntity instanceof ITranslatableBlockEntity translatableBlock))
                return;

            final double offsetValue = 1 / 16d;
            switch (direction) {
                case UP:
                    translatableBlock.setYOffset(translatableBlock.getYOffset() + offsetValue);
                    break;
                case DOWN:
                    translatableBlock.setYOffset(translatableBlock.getYOffset() - offsetValue);
                    break;
                case NORTH:
                    translatableBlock.setZOffset(translatableBlock.getZOffset() - offsetValue);
                    break;
                case SOUTH:
                    translatableBlock.setZOffset(translatableBlock.getZOffset() + offsetValue);
                    break;
                case WEST:
                    translatableBlock.setXOffset(translatableBlock.getXOffset() - offsetValue);
                    break;
                case EAST:
                    translatableBlock.setXOffset(translatableBlock.getXOffset() + offsetValue);
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
