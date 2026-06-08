package venomized.mods.extendedsignals.core.menu;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.CoreMenus;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntity;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableModelBlockEntity;

public class MenuModelConfig extends AbstractContainerMenu {
    private final Inventory inv;
    @Getter
    private BlockPos blockEntityPosition;

    /**
     * Clien    t
     *
     * @param menuModelConfigMenuType
     * @param i
     * @param buf
     */
    public MenuModelConfig(MenuType<MenuModelConfig> type, int i, Inventory inventory, FriendlyByteBuf buf) {
        this(type, i, inventory, buf.readBlockPos());
    }

    /**
     * Server
     *
     * @param menuModelConfigMenuType
     * @param i
     */
    public MenuModelConfig(MenuType<MenuModelConfig> type, int i, Inventory inventory, BlockPos bePos) {
        super(type, i);
        this.blockEntityPosition = bePos;
        this.inv = inventory;
    }

    public IConfigurableModelBlockEntity getReferenceBlockEntity() {
        return (IConfigurableModelBlockEntity) this.inv.player.level().getBlockEntity(blockEntityPosition);
    }

    /**
     * @param pPlayer
     * @param pIndex
     * @return
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    /**
     * @param pPlayer
     * @return
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return blockEntityPosition.closerThan(pPlayer.blockPosition(), 64d);
    }
}
