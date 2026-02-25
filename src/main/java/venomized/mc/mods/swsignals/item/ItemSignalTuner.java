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
import venomized.mc.mods.swsignals.item.components.SwComponents;

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

        ISignalTunerBindable.SignalTunerMode currentScroll = itemStack.getOrDefault(SwComponents.SIGNAL_TUNER_MODE, ISignalTunerBindable.SignalTunerMode.CONNECT);

        itemStack.set(SwComponents.SIGNAL_TUNER_MODE, currentScroll.next(up));

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

        ItemStack heldItem = pContext.getItemInHand();

        final ISignalTunerBindable.SignalTunerMode tunerMode = heldItem.getOrDefault(SwComponents.SIGNAL_TUNER_MODE, ISignalTunerBindable.SignalTunerMode.CONNECT);

        BlockEntity blockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());

        if (blockEntity instanceof ISignalTunerBindable currentTarget) {

            // We don't have one so we store the TARGET position
            if (!heldItem.has(SwComponents.BIND_LOCATION)) {
                System.out.println("Bind If Case");

                if (!currentTarget.isDestination()) {
                    System.out.println("Not a data destination: " + pContext.getClickedPos());
                    return InteractionResult.FAIL;
                }

                heldItem.set(SwComponents.BIND_LOCATION, pContext.getClickedPos());

                pContext.getPlayer().displayClientMessage(
                        Component.literal(
                                "Bind (Target) start: " + pContext.getClickedPos().toShortString()
                        ), true
                );

                return InteractionResult.SUCCESS;
            } else {
                System.out.println("Bind Else Case");

                BlockEntity destinationBlockEntity = pContext.getLevel().getBlockEntity(heldItem.get(SwComponents.BIND_LOCATION));

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

                heldItem.remove(SwComponents.BIND_LOCATION);
            }
        }

        return InteractionResult.PASS;
    }
}
