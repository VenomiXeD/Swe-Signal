package venomized.mc.mods.swsignals.client.ui;

import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import venomized.mc.mods.swsignals.client.SwMenus;
import venomized.mc.mods.swsignals.rail.SwedishSignalAspect;

import java.util.HashMap;


public class MenuTest extends AbstractContainerMenu {
    private final HashMap<SwedishSignalAspect, SwedishSignalAspect> manualOverrides = new HashMap<>();
    private final ContainerLevelAccess access;

    public MenuTest(int containerId, Inventory plrInventory, FriendlyByteBuf extraData) {
        this(containerId, plrInventory, ContainerLevelAccess.NULL);

        CompoundTag overrideTag = extraData.readAnySizeNbt().getCompound("overrides");
        for (String key : overrideTag.getAllKeys()) {
            manualOverrides.put(SwedishSignalAspect.valueOf(key), NBTHelper.readEnum(overrideTag, key, SwedishSignalAspect.class));
        }

    }

    public MenuTest(int containerId, Inventory plrInventory, ContainerLevelAccess pLevel) {
        super(SwMenus.MENU_TEST.get(), containerId);

        this.access = pLevel;
    }

    public HashMap<SwedishSignalAspect, SwedishSignalAspect> getManualOverrides() {
        return manualOverrides;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     *
     * @param pPlayer
     * @param pIndex
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    /**
     * Determines whether supplied player can use this container
     *
     * @param pPlayer
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
