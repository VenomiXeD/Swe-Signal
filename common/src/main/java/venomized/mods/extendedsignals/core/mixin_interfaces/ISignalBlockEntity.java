package venomized.mods.extendedsignals.core.mixin_interfaces;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.TriState;

@Deprecated
public interface ISignalBlockEntity {
    InteractionResult onRightClick(Level level, Player entity, TriState useItem, ItemStack itemStack);
}