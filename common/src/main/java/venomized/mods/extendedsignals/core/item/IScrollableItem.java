package venomized.mods.extendedsignals.core.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IScrollableItem {
    void onItemScroll(Player player, ItemStack itemStack, boolean up);
}
