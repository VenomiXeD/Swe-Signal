package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (railroadCrossingControllerPos != null) {
            tag.put("railroad_crossing_controller_pos", NbtUtils.writeBlockPos(this.railroadCrossingControllerPos));
        }
    }

    /**
     * @param tag
     * @param registries
     */
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("railroad_crossing_controller_pos")) {
            this.railroadCrossingControllerPos = NbtUtils.readBlockPos(tag, "railroad_crossing_controller_pos").orElse(null);
        }
    }
}
