package venomized.mc.mods.swsignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.BlockEntityRailroadCrossingController;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;

public class BlockRailroadCrossingController extends SwAbstractBlock implements EntityBlock {
    /**
     * @param pProperties
     */
    public BlockRailroadCrossingController() {
        super(Properties.copy(Blocks.IRON_BLOCK));
    }

    public BlockRailroadCrossingController(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return SeBlockEntities.BE_SE_RAILROAD_CROSSING_CONTROLLER.get().create(pPos, pState);
    }

    /**
     * Whether redstone dust should visually connect to this block on a given side
     * <p>
     * The default implementation is identical to
     * {@code RedStoneWireBlock#shouldConnectTo(BlockState, Direction)}
     *
     * <p>
     * {@link RedStoneWireBlock} updates its visual connection when
     * {@link BlockState#updateShape(Direction, BlockState, LevelAccessor, BlockPos, BlockPos)}
     * is called, this callback is used during the evaluation of its new shape.
     *
     * @param state     The current state
     * @param level     The level
     * @param pos       The block position in level
     * @param direction The coming direction of the redstone dust connection (with respect to the block at pos)
     * @return True if redstone dust should visually connect on the side passed
     * <p>
     * If the return value is evaluated based on level and pos (e.g. from BlockEntity), then the implementation of
     * this block should notify its neighbors to update their shapes when necessary. Consider using
     * {@link BlockState#updateNeighbourShapes(LevelAccessor, BlockPos, int, int)} or
     * {@link BlockState#updateShape(Direction, BlockState, LevelAccessor, BlockPos, BlockPos)}.
     * <p>
     * Example:
     * <p>
     * 1. {@code yourBlockState.updateNeighbourShapes(level, yourBlockPos, UPDATE_ALL);}
     * <p>
     * 2. {@code neighborState.updateShape(fromDirection, stateOfYourBlock, level, neighborBlockPos, yourBlockPos)},
     * where {@code fromDirection} is defined from the neighbor block's point of view.
     */
    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (pLevel.isClientSide) {
            return;
        }
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof BlockEntityRailroadCrossingController rcc) {
            rcc.setPowered(pLevel.hasNeighborSignal(pPos));
        }
    }
}
