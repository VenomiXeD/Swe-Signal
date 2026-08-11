package venomized.mods.extendedsignals.core.item;

import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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

    /**
     * @param itemStack
     * @param up
     */
    @Override
    public void onItemScroll(Player player, ItemStack itemStack, boolean up) {
        if (player.level().isClientSide()) {
            return;
        }

        CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        ISignalTunerToolable.SignalTunerMode currentMode = NBTHelper.readEnum(tag, TAG_MODE_NAME, ISignalTunerToolable.SignalTunerMode.class);
        ISignalTunerToolable.SignalTunerMode newMode = ISignalTunerToolable
                .SignalTunerMode.values()[
                Math.clamp(currentMode.ordinal() + (up ? 1 : -1), 0,
                        ISignalTunerToolable.SignalTunerMode.values().length - 1)
                ];

        NBTHelper.writeEnum(tag, TAG_MODE_NAME, newMode);

        player.displayClientMessage(Component.literal("Mode: %s".formatted(newMode.toString())).setStyle(
                        Style.EMPTY.withColor(ChatFormatting.GOLD)),
                true
        );

        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }


    /**
     * Called when this item is used when targeting a Block
     *
     * @param pContext
     */
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        final BlockEntity currentRightClickedBlockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
        if (!(currentRightClickedBlockEntity instanceof ISignalTunerToolable bindable)) {
            return InteractionResult.PASS;
        }

        CompoundTag tag = pContext.getItemInHand().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        ISignalTunerToolable.SignalTunerMode mode = NBTHelper.readEnum(tag, TAG_MODE_NAME, ISignalTunerToolable.SignalTunerMode.class);

        InteractionResult result = bindable.onSignalToolInteract(mode, pContext);

        if (pContext.getLevel().isClientSide()) {
            return result;
        }

        switch (mode) {
            case DISCONNECT:
                break;
            case CONNECT:
                if (!tag.contains(TAG_BLOCKENTITY_READER_NAME)) {
                    // Ensure that the reader block entity can only be a reader
                    if (!bindable.isReader()) {
                        pContext.getPlayer().displayClientMessage(Component.translatable("message.extended_signals.item.signalitem.not_reader").withStyle(ChatFormatting.RED), true);
                        result = InteractionResult.FAIL;
                        break;
                    }
                    pContext.getPlayer().displayClientMessage(Component.translatable("message.extended_signals.item.signalitem.binding_started").withStyle(ChatFormatting.YELLOW), true);
                    tag.put(TAG_BLOCKENTITY_READER_NAME, NbtUtils.writeBlockPos(currentRightClickedBlockEntity.getBlockPos()));
                    result = InteractionResult.SUCCESS;
                    break;
                }

                // Ensure that data source is only a source
                if (!bindable.isSource()) {
                    pContext.getPlayer().displayClientMessage(Component.translatable("message.extended_signals.item.signalitem.not_source").withStyle(ChatFormatting.RED), true);
                    result = InteractionResult.FAIL;
                    break;
                }

                final BlockEntity blockEntityReadingEntity =
                        NbtUtils.readBlockPos(tag, TAG_BLOCKENTITY_READER_NAME).map(pContext.getLevel()::getBlockEntity).orElse(null);

                if (!(blockEntityReadingEntity instanceof ISignalTunerToolable)) {
                    // TODO: inform player that the reader has been destroyed
                    pContext.getPlayer().displayClientMessage(Component.translatable("message.extended_signals.item.signalitem.reader_destroyed").withStyle(ChatFormatting.RED), true);
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

        pContext.getItemInHand().set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        return result;
    }

    /**
     * @return
     */
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        final ItemStack def = super.getDefaultInstance();

        final CompoundTag defaultTags = new CompoundTag();
        NBTHelper.writeEnum(defaultTags, TAG_MODE_NAME, ISignalTunerToolable.SignalTunerMode.CONNECT);

        def.set(DataComponents.CUSTOM_DATA, CustomData.of(defaultTags));

        return def;
    }


}
