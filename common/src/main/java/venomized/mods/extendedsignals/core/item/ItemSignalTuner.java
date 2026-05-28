package venomized.mods.extendedsignals.core.item;

import it.unimi.dsi.fastutil.Pair;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.ISignalTunerToolable;

public class ItemSignalTuner extends Item implements IScrollableItem {
    private static final String TAG_MODE_NAME = "mode";

    private static final String TAG_BLOCKENTITY_READER_NAME = "block_entity_pos_reader";

    public ItemSignalTuner(Properties pProperties) {
        super(pProperties);
    }

    private static void sendStatusMessageFromInteraction(UseOnContext pContext, Pair<InteractionResult, ? extends Component> result, MutableComponent fullMessage) {
        if (result.left() != null && result.right() != null) {
            switch (result.left()) {
                case SUCCESS:
                    pContext.getPlayer().sendSystemMessage(
                            fullMessage.setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
                    );
                    break;
                case FAIL:
                    pContext.getPlayer().sendSystemMessage(
                            fullMessage.setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                    );
                case PASS:
                    pContext.getPlayer().sendSystemMessage(
                            fullMessage
                    );
                    break;
            }
        }
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

        CompoundTag tag = itemStack.getOrCreateTag();

        ISignalTunerToolable.SignalTunerMode currentMode = NBTHelper.readEnum(tag, TAG_MODE_NAME, ISignalTunerToolable.SignalTunerMode.class);
        ISignalTunerToolable.SignalTunerMode newMode = ISignalTunerToolable
                .SignalTunerMode.values()[
                Math.min(
                        ISignalTunerToolable.SignalTunerMode.values().length - 1,
                        Math.max(0, currentMode.ordinal() + (up ? 1 : -1))
                )
                ];

        NBTHelper.writeEnum(tag, TAG_MODE_NAME, newMode);

        player.displayClientMessage(Component.literal("Mode: %s".formatted(newMode.toString())).setStyle(
                        Style.EMPTY.withColor(ChatFormatting.GOLD)),
                true
        );
    }

    /**
     * Called when this item is used when targeting a Block
     *
     * @param pContext
     */
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        final BlockEntity currentRightClickedBlockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
        if (!(currentRightClickedBlockEntity instanceof ISignalTunerToolable bindable)) {
            return InteractionResult.PASS;
        }

        CompoundTag tag = pContext.getItemInHand().getOrCreateTag();
        ISignalTunerToolable.SignalTunerMode mode = NBTHelper.readEnum(tag, TAG_MODE_NAME, ISignalTunerToolable.SignalTunerMode.class);

        InteractionResult result = bindable.onSignalToolInteract(mode, pContext);

        if (pContext.getLevel().isClientSide()) {
            return result;
        }

        switch (mode) {
            case DISCONNECT_ALL:
                break;
            case DISCONNECT:
                break;
            case CONNECT:
                if (!tag.contains(TAG_BLOCKENTITY_READER_NAME)) {
                    // Ensure that the reader block entity can only be a reader
                    if (!bindable.isReader()) {
                        result = InteractionResult.FAIL;
                        break;
                    }
                    tag.put(TAG_BLOCKENTITY_READER_NAME, NbtUtils.writeBlockPos(currentRightClickedBlockEntity.getBlockPos()));
                    result = InteractionResult.SUCCESS;
                    break;
                }

                // Ensure that data source is only a source
                if (!bindable.isSource()) {
                    result = InteractionResult.FAIL;
                    break;
                }

                final BlockEntity blockEntityReadingEntity = pContext.getLevel().getBlockEntity(
                        NbtUtils.readBlockPos(tag.getCompound(TAG_BLOCKENTITY_READER_NAME))
                );
                if (!(blockEntityReadingEntity instanceof ISignalTunerToolable)) {
                    // TODO: inform player that the reader has been destroyed
                    result = InteractionResult.FAIL;
                    tag.remove(TAG_BLOCKENTITY_READER_NAME);
                    break;
                }

                final BlockEntity blockEntitySourceEntity = currentRightClickedBlockEntity;

                final InteractionResult readerBindingResult = ((ISignalTunerToolable) blockEntityReadingEntity).readerBindingToSource(
                        (ISignalTunerToolable) blockEntitySourceEntity, mode, pContext
                );
                final InteractionResult sourceBindingResult = ((ISignalTunerToolable) blockEntitySourceEntity).sourceBindingToReader(
                        (ISignalTunerToolable) blockEntityReadingEntity, mode, pContext
                );

                tag.remove(TAG_BLOCKENTITY_READER_NAME);

                break;
            case CONFIGURE:
                break;
        }

        return result;
    }

    /**
     * @return
     */
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack def = super.getDefaultInstance();
        NBTHelper.writeEnum(def.getOrCreateTag(), TAG_MODE_NAME, ISignalTunerToolable.SignalTunerMode.CONNECT);

        return def;
    }


}
