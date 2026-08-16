package venomized.mods.extendedsignals.core.block.railway;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityTrainPathObserver;

public class BlockPathTrainDetector extends EdgePointBlock<BlockEntityTrainPathObserver> {
    /**
     * @param pProperties
     */
    public BlockPathTrainDetector(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntityTrainPathObserver> getBlockEntityClass() {
        return BlockEntityTrainPathObserver.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityTrainPathObserver> getBlockEntityType() {
        return CoreBlockEntities.PATH_TRAIN_DETECTOR.get();
    }

    /**
     * @param state     The current state
     * @param level     The level
     * @param pos       The block position in level
     * @param direction The coming direction of the redstone dust connection (with respect to the block at pos)
     * @return
     */
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    /**
     * @param pState
     * @return
     */
    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pDirection
     * @return
     */
    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        return pLevel.getBlockEntity(pPos, getBlockEntityType())
                .map(e -> e.redstoneOutput)
                .orElse(0);
    }
}
