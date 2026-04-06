package venomized.mods.swsignal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.SwAbstractBlock;
import venomized.mc.mods.swsignals.blockentity.ExtendedSignalBlockEntity;

/**
 * Any object that can have 45 degrees rotation.
 * Needs a Block Entity Renderer
 */
public class SwRotateableBlock extends SwAbstractBlock {
    public SwRotateableBlock(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion());
    }

    public static int get8Direction(float yaw) {
        yaw += 180f;
        int index = Math.floorMod(Mth.floor((yaw * 16.0F / 360.0F) + 0.5D), 16);
        return index;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    }

    /**
     * @param pContext
     * @return
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {

        return this.defaultBlockState();
    }

    /**
     * @param pLevel
     * @param pPos
     * @param pState
     * @param pPlacer
     * @param pStack
     */
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (pPlacer == null) return;


        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof ExtendedSignalBlockEntity esbe) {
            int orientation = get8Direction(pPlacer.getYRot());
            esbe.setOrientationIndex(orientation);
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
}
