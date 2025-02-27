package venomized.mc.mods.swsignals.block;

import com.simibubi.create.AllTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.blockentity.BlockEntitySignalBlock;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public abstract class BlockAbstractSignal extends SwAbstractBlock implements EntityBlock {
	public static BooleanProperty MOUNTED = BooleanProperty.create("mounting");
	public static IntegerProperty ORIENTATION = IntegerProperty.create("orientation", 0, 7);



	public BlockAbstractSignal() {
		super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY));
		this.registerDefaultState(this.stateDefinition.any().setValue(MOUNTED, false).setValue(ORIENTATION, 0));
	}

	private static int get8Direction(float yaw) {
		yaw += 180;
		int index = Mth.floor((yaw + 22.5F) / 45.0F) & 7; // Convert to 8 steps
		return index;
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder
				.add(MOUNTED)
				.add(ORIENTATION);
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
	 * @param pContext
	 * @return
	 */
	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
		int orientation = get8Direction(pContext.getRotation());
		return this.defaultBlockState()
				.setValue(ORIENTATION,orientation);
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
}
