package venomized.mc.mods.swsignals.block.sw;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.SwAbstractBlock;
import venomized.mc.mods.swsignals.blockentity.sw.BlockEntitySignalBox;
import venomized.mc.mods.swsignals.client.ui.MenuTest;

public class BlockSignalBox extends SwAbstractBlock implements EntityBlock {
	public static final String BLOCK_NAME = "sw_signal_box";

	public BlockSignalBox() {
		super(Properties.copy(Blocks.IRON_BLOCK));
	}

	/**
	 * @param pPos
	 * @param pState
	 * @return
	 */
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new BlockEntitySignalBox(pPos, pState);
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
		return EntityBlock.super.getTicker(pLevel, pState, pBlockEntityType);
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
		// if (pLevel.isClientSide()) {
		// 	return InteractionResult.SUCCESS;
		// };

		// if(pPlayer.getItemInHand(pHand).isEmpty()) {
		// 	NetworkHooks.openScreen((ServerPlayer) pPlayer, pState.getMenuProvider(pLevel, pPos), (data)->
		// 			data.writeUtf("Hello world!")
		// 	);
		// }

		// return InteractionResult.sidedSuccess(pLevel.isClientSide());
		return InteractionResult.SUCCESS;
	}

	/**
	 * @param pState
	 * @param pLevel
	 * @param pPos
	 * @deprecated
	 */
	@Override
	public @Nullable MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
		return new SimpleMenuProvider((pId, pInventory, pPlayer) -> new MenuTest(pId, pInventory, ContainerLevelAccess.create(pLevel, pPos)), Component.translatable("menu.title.swsignal.mymenu"));
	}
}
