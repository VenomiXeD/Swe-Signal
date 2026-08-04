package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntityATCController;

public class BlockATCController extends ExtendedSignalsBlock implements IBE {
    /**
     * @param properties
     */
    public BlockATCController(Properties properties) {
        super(properties);
    }

    @Override
    public Class getBlockEntityClass() {
        return BlockEntityATCController.class;
    }

    @Override
    public BlockEntityType<BlockEntityATCController> getBlockEntityType() {
        return null; // CoreBlockEntities.ATC_CONTROLLER.get();
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pNewState
     * @param pMovedByPiston
     * @deprecated
     */
    @Deprecated
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
    }
}
