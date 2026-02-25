package venomized.mc.mods.swsignals.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import venomized.mc.mods.swsignals.core.SwSignal;
import venomized.mc.mods.swsignals.item.IScrollableItem;
import venomized.mc.mods.swsignals.network.packets.ClientScrollNetworkEventPacket;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = SwSignal.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onScrollEvent(InputEvent.MouseScrollingEvent e) {
        if (Screen.hasControlDown()) {
            ItemStack mainHandItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableInterface) {
                PacketDistributor.sendToServer(new ClientScrollNetworkEventPacket(e.getScrollDeltaY() > 0));

                scrollableInterface.onItemScroll(Minecraft.getInstance().player, mainHandItem, e.getScrollDeltaY() > 0);

                e.setCanceled(true);
            }
        }
    }
}
