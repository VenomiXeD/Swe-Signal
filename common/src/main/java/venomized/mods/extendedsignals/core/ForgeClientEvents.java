package venomized.mods.extendedsignals.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import venomized.mods.extendedsignals.ExtendedSignalsCore;
import venomized.mods.extendedsignals.item.IScrollableItem;
import venomized.mods.extendedsignals.network.Networking;
import venomized.mods.extendedsignals.network.packets.ClientScrollNetworkEventPacket;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ExtendedSignalsCore.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onScrollEvent(InputEvent.MouseScrollingEvent e) {
        if (Screen.hasControlDown()) {
            ItemStack mainHandItem = Minecraft.getInstance().player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IScrollableItem scrollableInterface) {
                Networking.CHANNEL.sendToServer(new ClientScrollNetworkEventPacket(e.getScrollDelta() > 0));

                scrollableInterface.onItemScroll(Minecraft.getInstance().player, mainHandItem, e.getScrollDelta() > 0);

                e.setCanceled(true);
            }
        }
    }
}
