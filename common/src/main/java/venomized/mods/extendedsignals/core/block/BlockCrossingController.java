package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;

public class BlockCrossingController extends ExtendedSignalsBlock implements EntityBlock {
    /**
     * @param pProperties
     */
    public BlockCrossingController(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pPos
     * @param pState
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return CoreBlockEntities.CROSSING_CONTROLLER.create(pPos, pState);
    }
}
