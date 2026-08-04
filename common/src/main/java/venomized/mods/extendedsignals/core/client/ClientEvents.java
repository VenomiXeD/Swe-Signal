package venomized.mods.extendedsignals.core.client;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.actors.trainControls.ControlsHandler;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;
import venomized.mods.extendedsignals.core.item.IScrollableItem;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundRequestShuntPacket;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundScrollItemPacket;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundTranslateBlockPacket;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onScrollEvent(InputEvent.MouseScrollingEvent e) {
        if (Screen.hasControlDown() && Minecraft.getInstance().player != null) {
            ItemStack mainHandItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableInterface) {
                PacketDistributor.sendToServer(new ServerBoundScrollItemPacket(e.getScrollDeltaY() > 0));
                scrollableInterface.onItemScroll(Minecraft.getInstance().player, mainHandItem, e.getScrollDeltaY() > 0);

                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientPlayerTickEvent(PlayerTickEvent.Post e) {
        if (KeyMappings.REQUEST_SHUNT.consumeClick()) {
            if (ControlsHandler.getContraption() instanceof CarriageContraptionEntity trainCarriage) {
                System.out.println("train: " + trainCarriage.getCarriage().train.id);

                float carriageYRot = trainCarriage.getYRot() - (trainCarriage.yaw + 90);
                float playerYRot = e.getEntity().getYRot();

                float rotDiff = Mth.wrapDegrees(playerYRot - carriageYRot);

                boolean front = Mth.abs(rotDiff) >= 90f;
                if (trainCarriage.checkConductors().getSecond().booleanValue())
                    front = !front;

                PacketDistributor.sendToServer(new ServerBoundRequestShuntPacket(trainCarriage.getCarriage().train.id, front, 64));
            } else {
                System.out.println("none");
            }
        }
    }


    @SubscribeEvent
    public static void onClientInteractOnBlock(PlayerInteractEvent.RightClickBlock e) {
        if (!AllItems.WRENCH.is(e.getItemStack()))
            return;

        if (!(e.getLevel().getBlockEntity(e.getPos()) instanceof IConfigurableModelBlockEntity))
            return;

        Direction dir = e.getHitVec().getDirection().getOpposite();
        if (Screen.hasControlDown())
            dir = dir.getOpposite();

        // TODO: Networking
        PacketDistributor.sendToServer(new ServerBoundTranslateBlockPacket(e.getPos(), dir));

        e.setCanceled(true);
        e.setCancellationResult(InteractionResult.SUCCESS);
    }
}