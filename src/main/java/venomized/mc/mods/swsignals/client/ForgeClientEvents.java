package venomized.mc.mods.swsignals.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import venomized.mc.mods.swsignals.item.IScrollableItem;
import venomized.mc.mods.swsignals.network.ClientScrollNetworkEvent;
import venomized.mc.mods.swsignals.network.Networking;

@OnlyIn(Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public void onScrollEvent(InputEvent.MouseScrollingEvent e) {
        if (Screen.hasControlDown()) {
            ItemStack mainHandItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableInterface) {
                Networking.CHANNEL.sendToServer(new ClientScrollNetworkEvent(e.getScrollDelta() > 0));

                scrollableInterface.onItemScroll(Minecraft.getInstance().player, mainHandItem, e.getScrollDelta() > 0);

                e.setCanceled(true);
            }
        }
    }
}
