package venomized.mc.mods.swsignals.block;

import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Any object that can have 45 degrees rotation.
 * Needs a Block Entity Renderer
 */
public class Sw45DegreeBlock extends SwAbstractBlock {
    public static IntegerProperty ORIENTATION = IntegerProperty.create("orientation", 0, 7);

    public Sw45DegreeBlock(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion());
    }

    public static int get8Direction(float yaw) {
        yaw += 180f;
        int index = Mth.floor((yaw + 22.5f) / 45.0f) & 7; // Convert to 8 steps
        return index;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder
                .add(ORIENTATION);
    }

    /**
     * @param pContext
     * @return
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        int orientation = get8Direction(pContext.getRotation());
        return this.defaultBlockState()
                .setValue(ORIENTATION, orientation);
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
}
