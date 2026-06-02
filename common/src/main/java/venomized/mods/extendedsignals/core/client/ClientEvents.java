package venomized.mods.extendedsignals.core.client;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.blockentity.ITranslatableBlockEntity;
import venomized.mods.extendedsignals.core.item.IScrollableItem;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundRequestShuntPacket;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundScrollItemPacket;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundTranslateBlockPacket;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ExtendedSignalsCore.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onScrollEvent(InputEvent.MouseScrollingEvent e) {
        if (Screen.hasControlDown() && Minecraft.getInstance().player != null) {
            ItemStack mainHandItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableInterface) {
                ExtendedSignalsNetworking.CHANNEL.sendToServer(new ServerBoundScrollItemPacket(e.getScrollDelta() > 0));

                scrollableInterface.onItemScroll(Minecraft.getInstance().player, mainHandItem, e.getScrollDelta() > 0);

                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientPlayerTickEvent(TickEvent.ClientTickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            if (KeyMappings.REQUEST_SHUNT.consumeClick()) {
                if (ControlsHandler.getContraption() instanceof CarriageContraptionEntity trainCarriage) {
                    System.out.println("train: " + trainCarriage.getCarriage().train.id);

                    float carriageYRot = trainCarriage.getYRot() - (trainCarriage.yaw + 90);
                    float playerYRot = e.player.getYRot();

                    float rotDiff = Mth.wrapDegrees(playerYRot - carriageYRot);

                    boolean front = Mth.abs(rotDiff) >= 90f;
                    if (trainCarriage.checkConductors().getSecond().booleanValue())
                        front = !front;

                    ExtendedSignalsNetworking.CHANNEL.sendToServer(
                            new ServerBoundRequestShuntPacket(trainCarriage.getCarriage().train.id, front, 64)
                    );
                } else {
                    System.out.println("none");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onClientInteractOnBlock(PlayerInteractEvent.RightClickBlock e) {
        if (!e.getItemStack().is(AllTags.AllItemTags.WRENCH.tag))
            return;

        if (!(e.getLevel().getBlockEntity(e.getPos()) instanceof ITranslatableBlockEntity))
            return;

        Direction dir = e.getHitVec().getDirection().getOpposite();
        if (Screen.hasControlDown())
            dir = dir.getOpposite();

        ExtendedSignalsNetworking.CHANNEL
                .sendToServer(new ServerBoundTranslateBlockPacket(e.getPos(), dir));

        e.setCanceled(true);
        e.setCancellationResult(InteractionResult.SUCCESS);
    }
}
