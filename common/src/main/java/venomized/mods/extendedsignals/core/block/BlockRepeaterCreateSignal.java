package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityRepeaterCreateSignal;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;

public class BlockRepeaterCreateSignal extends ExtendedSignalsBlock implements IBE {
    /**
     * @param pProperties
     */
    public BlockRepeaterCreateSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class getBlockEntityClass() {
        return BlockEntityRepeaterCreateSignal.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType getBlockEntityType() {
        return CoreBlockEntities.SIGNAL_REPEATER.get();
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pNewState
     * @param pMovedByPiston
     */
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        IBE.onRemove(pState, pLevel, pPos, pNewState);
    }
}
