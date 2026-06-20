package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityCrossingController extends CoreBlockEntity implements ISignalTunerToolable {
    public BlockEntityCrossingController(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public boolean isRedstonePowered() {
        return getLevel().hasNeighborSignal(worldPosition);
    }

    /**
     * @return
     */
    @Override
    public boolean supportsConfiguration() {
        return false;
    }
}
