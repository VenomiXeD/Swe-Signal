package venomized.mc.mods.swsignals.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import venomized.mc.mods.swsignals.block.SwBlocks;

public class ItemTest extends Item {

	public ItemTest() {
		super(new Properties());
	}

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUseFirst(ItemStack, UseOnContext)}.
	 *
	 * @param pLevel
	 * @param pPlayer
	 * @param pUsedHand
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (pLevel.isClientSide) {
			return InteractionResultHolder.success(new ItemStack(this));
		}

		BlockHitResult clip = pLevel.clip(new ClipContext(
				pPlayer.getEyePosition(), pPlayer.getEyePosition().add(pPlayer.getLookAngle().scale(5)),
				ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pPlayer
		));

		if (clip != null) {
			BlockState bs;
			bs = pLevel.getBlockState(clip.getBlockPos());

			BlockPos p = clip.getBlockPos().relative(clip.getDirection());
			bs = pLevel.getBlockState(p);
			if(bs.isAir()) {
				pLevel.setBlockAndUpdate(p, SwBlocks.BLOCK_TWO_LIGHT_SIGNAL.get().defaultBlockState());
			}
		}
		return super.use(pLevel, pPlayer, pUsedHand);
	}
}
