package venomized.mods.extendedsignals.core.network.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;

import java.util.function.Supplier;

public record ServerBoundModelConfigurePacket(BlockPos pos, Vec3 loc, Vec3 gbl,
                                              Vec3 orientation) implements ISimplePacket {
    public static ServerBoundModelConfigurePacket decode(FriendlyByteBuf buf) {
        return new ServerBoundModelConfigurePacket(
                buf.readBlockPos(),
                new Vec3(buf.readVector3f()),
                new Vec3(buf.readVector3f()),
                new Vec3(buf.readVector3f())
        );
    }

    /**
     * @param contextSupplier
     */
    @Override
    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            BlockEntity blockEntity = contextSupplier.get().getSender().serverLevel()
                    .getBlockEntity(pos);

            if (blockEntity instanceof IConfigurableModelBlockEntity configurableModel) {
                configurableModel.setLocOffset(this.loc);
                configurableModel.setGblOffset(this.gbl);
                configurableModel.setOrientation(this.orientation);
            }
        });

        contextSupplier.get().setPacketHandled(true);
    }

    /**
     * @param buf
     */
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeVector3f(loc.toVector3f());
        buf.writeVector3f(gbl.toVector3f());
        buf.writeVector3f(orientation.toVector3f());
    }
}
