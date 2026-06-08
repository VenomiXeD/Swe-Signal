package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPathTrainDetector;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;

public class BlockPathTrainDetector extends EdgePointBlock<BlockEntityPathTrainDetector> implements EntityBlock {
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
    public Class<BlockEntityPathTrainDetector> getBlockEntityClass() {
        return BlockEntityPathTrainDetector.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<BlockEntityPathTrainDetector> getBlockEntityType() {
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
