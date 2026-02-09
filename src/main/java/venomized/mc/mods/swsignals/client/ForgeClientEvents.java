package venomized.mc.mods.swsignals.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import venomized.mc.mods.swsignals.core.SwSignal;
import venomized.mc.mods.swsignals.item.IScrollableItem;
import venomized.mc.mods.swsignals.network.packets.ClientScrollNetworkEventPacket;
import venomized.mc.mods.swsignals.network.Networking;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SwSignal.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public void onScrollEvent(InputEvent.MouseScrollingEvent e) {
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
