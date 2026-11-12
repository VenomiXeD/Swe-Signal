package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.ExtendedSignals;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BlockWithEntityWrapper extends ExtendedSignalsBlock implements IAutoBlockEntityBlock {
    protected Supplier<BiFunction<BlockPos, BlockState, BlockEntity>> blockEntityFactory;


    /**
     * @param pProperties
     */
    public BlockWithEntityWrapper(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param factory
     */
    @Override
    public void setBlockEntityType(BiFunction<BlockPos, BlockState, BlockEntity> factory) {
        if (this.blockEntityFactory != null) {
            throw new IllegalStateException("Already initialized with a BlockEntityFactory");
        }
        ExtendedSignals.LOGGER.info("Attached BlockEntityFactory to BlockModelled");
        blockEntityFactory = () -> factory;
    }

    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (blockEntityFactory == null) {
            ExtendedSignals.LOGGER.fatal("BlockEntityFactory for a BlockWithEntityWrapper is null; This shouldn't happen!");
            return null;
        }
        return blockEntityFactory.get().apply(pos, state);
    }

    /**
     * @param state
     * @param level
     * @param pos
     * @param newState
     * @param movedByPiston
     */
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        IBE.onRemove(state, level, pos, newState);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
