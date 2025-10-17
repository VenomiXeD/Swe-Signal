package venomized.mc.mods.swsignals.item;

import it.unimi.dsi.fastutil.Pair;
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
import venomized.mc.mods.swsignals.blockentity.ISignalTunerBindable;

import java.util.Optional;

public class ItemSignalTuner extends Item implements IScrollableItem {
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

        ISignalTunerBindable.SignalTunerMode currentScroll = ISignalTunerBindable.SignalTunerMode.CONNECT;
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains("mode")) {
            currentScroll = ISignalTunerBindable.SignalTunerMode.values()[tag.getInt("mode")];
        }
        int newMode = Math.min(ISignalTunerBindable.SignalTunerMode.values().length - 1, Math.max(0, currentScroll.ordinal() + (up ? 1 : -1)));
        tag.putInt("mode", newMode);
        player.displayClientMessage(Component.literal("Mode: %s".formatted(currentScroll.toString())).setStyle(
                        Style.EMPTY.withColor(ChatFormatting.GOLD)),
                true
        );

        System.out.println("current mode: " + currentScroll);
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
            return InteractionResult.sidedSuccess(pContext.getLevel().isClientSide());
        }

        System.out.println("useOn");

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
                System.out.println("Bind If Case");

                if (!currentTarget.isDestination()) {
                    System.out.println("Not a data destination: " + pContext.getClickedPos());
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
                System.out.println("Bind Else Case");

                BlockEntity destinationBlockEntity = pContext.getLevel().getBlockEntity(NbtUtils.readBlockPos(tag.getCompound("bind_location_start")));

                Optional<ISignalTunerBindable> source = Optional.ofNullable(currentTarget);

                Optional<ISignalTunerBindable> destination;
                if (destinationBlockEntity instanceof ISignalTunerBindable) {
                    destination = Optional.ofNullable((ISignalTunerBindable) destinationBlockEntity);
                } else {
                    destination = Optional.empty();
                }

                if (source.isPresent()) {
                    Pair<InteractionResult, ? extends Component> result = destination.get().onBindToSource(source, tunerMode);

                    if (result.right() != null) {
                        MutableComponent fullMessage = Component.literal("[SOURCE] ").append(result.right() != null ? result.right() : Component.empty());
                        sendStatusMessageFromInteraction(pContext, result, fullMessage);
                    }
                }

                if (destination.isPresent()) {
                    Pair<InteractionResult, ? extends Component> result = source.get().onBindToTarget(destination, tunerMode);

                    if (result.right() != null) {
                        MutableComponent fullMessage = Component.literal("[TARGET] ").append(result.right());
                        sendStatusMessageFromInteraction(pContext, result, fullMessage);
                    }
                }

                tag.remove("bind_location_start");
            }
        }

        return InteractionResult.PASS;
    }
}
