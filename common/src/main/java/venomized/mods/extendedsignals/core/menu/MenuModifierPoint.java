package venomized.mods.extendedsignals.core.menu;

import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPointModifier;

public class MenuModifierPoint extends AbstractContainerMenu {
    @Getter
    private final BlockEntityPointModifier<?> targetBlockEntity;

    public MenuModifierPoint(@Nullable MenuType<?> menuType, int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(menuType, containerId, inv, (BlockEntityPointModifier<?>) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public MenuModifierPoint(@Nullable MenuType<?> menuType, int containerId, Inventory inv, BlockEntityPointModifier<?> blockEntityPointModifier) {
        super(menuType, containerId);

        targetBlockEntity = blockEntityPointModifier;
    }

    /**
     * @param player
     * @param index
     * @return
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
