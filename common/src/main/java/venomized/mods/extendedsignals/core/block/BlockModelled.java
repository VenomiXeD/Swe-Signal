package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BlockModelled extends ExtendedSignalsBlock {


    /**
     * @param pProperties
     */
    public BlockModelled(Properties pProperties) {
        super(pProperties);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @param pState
     * @deprecated call via {@link BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    @Deprecated
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
