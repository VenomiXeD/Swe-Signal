package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.ExtendedSignalBlock;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;

public abstract class BlockSignal extends ExtendedSignalBlock implements EntityBlock {
    public BlockSignal(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion().dynamicShape().pushReaction(PushReaction.DESTROY));
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pContext
     * @deprecated
     */
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block();
    }

    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @param pContext
     * @deprecated
     */
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block();
    }



    /**
     * @param pState
     * @param pLevel
     * @param pPos
     * @deprecated
     */
    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.create(
                AABB.ofSize(pPos.getCenter(), 1, 2, 1)
        );
    }
}
