package venomized.mc.mods.swsignals.item;

import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mc.mods.swsignals.blockentity.BlockEntitySignalBlock;

public class CreateSignalTestItem extends Item {
	private static final String TAG = "SW_TEST";

	public CreateSignalTestItem() {
		super(new Properties());
	}

	/**
	 * Called when this item is used when targeting a Block
	 *
	 * @param pContext
	 */
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		BlockPos pPos = pContext.getClickedPos();
		BlockEntity blockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());

		CompoundTag itemData = pContext.getItemInHand().getOrCreateTag();

		if (blockEntity instanceof BlockEntitySignalBlock) {
			itemData.putIntArray("sw_signal_pos", new int[]{pPos.getX(), pPos.getY(), pPos.getZ()});
		}
		else if (blockEntity instanceof SignalBlockEntity) {
			itemData.putIntArray("cr_signal_pos", new int[]{pPos.getX(), pPos.getY(), pPos.getZ()});
		}

		if (itemData.contains("sw_signal_pos") && itemData.contains("cr_signal_pos")) {
			int[] swSignalPos = itemData.getIntArray("sw_signal_pos");
			int[] crSignalPos = itemData.getIntArray("cr_signal_pos");
			BlockPos sw_signal_pos = new BlockPos(swSignalPos[0],swSignalPos[1],swSignalPos[2]);
			BlockPos cr_signal_pos =  new BlockPos(crSignalPos[0],crSignalPos[1],crSignalPos[2]);

			blockEntity = pContext.getLevel().getBlockEntity(sw_signal_pos);

			if (blockEntity instanceof BlockEntitySignalBlock b) {
				b.setTargetedSignal(cr_signal_pos);
			}

			itemData.remove("sw_signal_pos");
			itemData.remove("cr_signal_pos");
		}


		return InteractionResult.PASS;
	}
}
