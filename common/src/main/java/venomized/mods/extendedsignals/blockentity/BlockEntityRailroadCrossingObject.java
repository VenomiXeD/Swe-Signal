package venomized.mods.extendedsignals.blockentity;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BlockEntityRailroadCrossingObject extends ExtendedSignalsCoreBlockEntity implements ISignalTunerBindable {
    @Nullable
    public BlockPos railroadCrossingControllerPos;

    public BlockEntityRailroadCrossingObject(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public boolean isRailroadCrossingControllerPowered() {
        if (railroadCrossingControllerPos != null) {
            return getLevel().getBlockEntity(railroadCrossingControllerPos) instanceof BlockEntityRailroadCrossingController c && c.isPowered();
        }
        return false;
    }

    public void setRailroadCrossingControllerPos(BlockPos p) {
        railroadCrossingControllerPos = p;
        this.updateSelf();
    }

    /**
     * Signal Box A -> Create Signal; Create Signal is the source
     *
     * @param sourceBlockEntity source block destination
     * @param mode
     * @return
     */
    @Override
    public Pair<InteractionResult, MutableComponent> sourceBindingToReader(Optional<ISignalTunerBindable> sourceBlockEntity, SignalTunerMode mode) {
        if (sourceBlockEntity.isPresent()) {
            IForgeBlockEntity be = sourceBlockEntity.get();
            if (be instanceof BlockEntityRailroadCrossingController c) {
                setRailroadCrossingControllerPos(c.getBlockPos());
                return Pair.of(InteractionResult.SUCCESS, Component.literal("Successfully bound to controller"));
            }
        }
        return Pair.of(InteractionResult.PASS, null);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (railroadCrossingControllerPos != null) {
            pTag.put("railroad_crossing_controller_pos", NbtUtils.writeBlockPos(this.railroadCrossingControllerPos));
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("railroad_crossing_controller_pos")) {
            this.railroadCrossingControllerPos = NbtUtils.readBlockPos(pTag.getCompound("railroad_crossing_controller_pos"));
        }
    }


    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
     */
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }
}
