package venomized.mods.extendedsignals.core.block;

import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.ModelBlockEntity;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BlockModelled extends ExtendedSignalsBlock implements EntityBlock {
    private Supplier<BiFunction<BlockPos, BlockState, BlockEntity>> blockEntityFactory;

    public static <BE extends ModelBlockEntity> NonNullFunction<Properties, BlockModelled> withBlockEntity(Supplier<BiFunction<BlockPos, BlockState, BlockEntity>> blockEntityFactory) {
        return (prop) -> {
            BlockModelled block = new BlockModelled(prop);
            block.blockEntityFactory = blockEntityFactory;
            return block;
        };
    }

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


    /**
     * @param pos
     * @param state
     * @return
     */
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (blockEntityFactory == null)
            return null;
        return blockEntityFactory.get().apply(pos, state);
    }
}
