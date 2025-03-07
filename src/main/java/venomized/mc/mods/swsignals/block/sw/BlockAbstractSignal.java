package venomized.mc.mods.swsignals.block.sw;

import com.simibubi.create.AllTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.SwAbstract45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.se.BlockEntitySignal;

public abstract class BlockAbstractSignal extends SwAbstract45DegreeBlock implements EntityBlock {
	public static BooleanProperty MOUNTED = BooleanProperty.create("mounting");

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		// TODO Auto-generated method stub
		return null;
	}

	public BlockAbstractSignal() {
		super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape().pushReaction(PushReaction.DESTROY));
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		super.createBlockStateDefinition(pBuilder);
		pBuilder
				.add(MOUNTED);
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
	 * @param pLevel
	 * @param pState
	 * @param pBlockEntityType
	 * @param <T>
	 * @return
	 */
	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		return BlockEntitySignal::worldTick;
	}

	/**
	 * @param pContext
	 * @return
	 */
	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
		return super.getStateForPlacement(pContext).setValue(MOUNTED, false);
	}

	/**
	 * @param pState
	 * @param pLevel
	 * @param pPos
	 * @param pPlayer
	 * @param pHand
	 * @param pHit
	 * @deprecated
	 */
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (!pLevel.isClientSide) {
			ItemStack itemInHand = pPlayer.getItemInHand(pHand);
			if (itemInHand.is(AllTags.AllItemTags.WRENCH.tag)) {
				pLevel.setBlock(pPos, pState.setValue(MOUNTED, !pState.getValue(MOUNTED)), 2);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
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
