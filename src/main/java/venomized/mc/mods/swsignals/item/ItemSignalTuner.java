package venomized.mc.mods.swsignals.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;

import java.util.Optional;

public class ItemSignalTuner extends Item implements IScrollableItem {
	public ItemSignalTuner() {
		super(new Properties());
	}

	/**
	 * @param itemStack
	 * @param up
	 */
	@Override
	public void onItemScroll(Player player, ItemStack itemStack, boolean up) {
		if (player.level().isClientSide()) {
			return;
		}

		ISignalTunerBindable.SignalTunerMode currentScroll = ISignalTunerBindable.SignalTunerMode.CONNECT;
		CompoundTag tag = itemStack.getOrCreateTag();
		if (tag.contains("mode")) {
			currentScroll = ISignalTunerBindable.SignalTunerMode.values()[tag.getInt("mode")];
		}
		tag.putInt("mode",
				Math.max(ISignalTunerBindable.SignalTunerMode.values().length - 1, Math.min(0, currentScroll.ordinal() + (up ? 1 : -1)))
		);
		player.displayClientMessage(Component.literal("Mode: %d".formatted(currentScroll)).setStyle(
						Style.EMPTY.withColor(ChatFormatting.GOLD)),
				true
		);
		// Minecraft.getInstance().level.playSound(
		// 		Minecraft.getInstance().player,
		// 		Minecraft.getInstance().player,
		// 		AllSoundEvents.SCROLL_VALUE.getMainEvent(), SoundSource.MASTER, 1f, 1f + currentScroll/10f
		// );
	}

	/**
	 * Called when this item is used when targeting a Block
	 *
	 * @param pContext
	 */
	@Override
	public InteractionResult useOn(UseOnContext pContext) {
		if (pContext.getLevel().isClientSide()) {
			pContext.getPlayer().openMenu(new MenuType<>());
			return InteractionResult.SUCCESS;
		}

		ISignalTunerBindable.SignalTunerMode mode = ISignalTunerBindable.SignalTunerMode.CONNECT;
		CompoundTag tag = pContext.getItemInHand().getOrCreateTag();
		if (tag.contains("mode")) {
			mode = ISignalTunerBindable.SignalTunerMode.values()[tag.getInt("mode")];
		}
		BlockEntity blockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());

		final ISignalTunerBindable.SignalTunerMode tunerMode = mode;
		if (blockEntity instanceof ISignalTunerBindable currentTarget) {
			// We don't have one so we store the TARGET position
			if (!tag.contains("bind_location_start")) {

				if (!currentTarget.isTarget()) {
					System.out.println("not a target: " + currentTarget);
					return InteractionResult.FAIL;
				}

				tag.put("bind_location_start", NbtUtils.writeBlockPos(pContext.getClickedPos()));

				pContext.getPlayer().displayClientMessage(
						Component.literal(
								"Bind (Target) start: " + pContext.getClickedPos().toShortString()
						), true
				);

				return InteractionResult.SUCCESS;
			} else {
				BlockEntity targetBlockEntity = pContext.getLevel().getBlockEntity(NbtUtils.readBlockPos(tag.getCompound("bind_location_start")));
				Optional<ISignalTunerBindable> source = Optional.of(currentTarget);
				Optional<ISignalTunerBindable> target;
				if (targetBlockEntity instanceof ISignalTunerBindable) {
					target = Optional.of((ISignalTunerBindable) targetBlockEntity);
				} else {
					target = Optional.empty();
				}

				source.ifPresent(sourceBlockEntity -> sourceBlockEntity.onBindToTarget(target, tunerMode));
				target.ifPresent(sourceBlockEntity -> sourceBlockEntity.onBindToSource(source, tunerMode));

				tag.remove("bind_location_start");
			}
		}

		return InteractionResult.PASS;
	}
}
