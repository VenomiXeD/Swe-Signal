package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class BlockEntityCrossingObject extends CoreBlockEntity implements ISignalTunerToolable {
    @Nullable
    public BlockPos railroadCrossingControllerPos;

    public BlockEntityCrossingObject(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void setRailroadCrossingControllerPos(BlockPos p) {
        railroadCrossingControllerPos = p;
        this.sync();
    }

    public boolean isActive() {
        if (railroadCrossingControllerPos == null)
            return false;
        return getLevel().getBlockEntity(railroadCrossingControllerPos, CoreBlockEntities.CROSSING_CONTROLLER.get())
                .map(BlockEntityCrossingController::isRedstonePowered)
                .orElse(false);
    }

    /**
     * @param targetBlockEntity target block destination
     * @param mode
     * @param useContext
     * @return
     */
    @Override
    public InteractionResult readerBindingToSource(ISignalTunerToolable targetBlockEntity, SignalTunerMode mode, UseOnContext useContext) {
        if (targetBlockEntity != null) {
            if (targetBlockEntity instanceof BlockEntityCrossingController crossingController) {
                setRailroadCrossingControllerPos(crossingController.getBlockPos());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
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
