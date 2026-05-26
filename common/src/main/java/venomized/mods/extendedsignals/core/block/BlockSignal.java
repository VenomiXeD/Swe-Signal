package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;

/**
 * General purpose, generic signal block intended to cover most basic signals
 */
public abstract class BlockSignal extends ExtendedSignalsBlock implements EntityBlock {
    /**
     * @param pProperties
     */
    public BlockSignal(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @param pLevel
     * @param pState
     * @param pBlockEntityType
     * @param <T>
     * @return
     */
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return (level, pos, state, be) -> {
                if (be instanceof BlockEntitySignal<?> signal) {
                    BlockEntitySignal.clientTick(signal, pLevel, pos, state);
                }
            };
        } else {
            return (level, pos, state, be) -> {
                if (be instanceof BlockEntitySignal<?> signal) {
                    BlockEntitySignal.serverTick(signal, level, pos, state);
                }
            };
        }
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @param pState
     * @deprecated call via {@link BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @return
     */
    @Override
    public boolean isOcclusionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return false;
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @return
     */
    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pContext
     * @return
     */
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @return
     */
    @Override
    public boolean isCollisionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return false;
    }

    /**
     * @param pState
     * @return
     */
    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return false;
    }
}
