package venomized.mc.mods.swsignals.client.ui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;
import venomized.mc.mods.swsignals.client.SwMenus;


public class MenuTest extends AbstractContainerMenu {
	private String signalDesignation;

	private ContainerLevelAccess access;

	public MenuTest(int containerId, Inventory plrInventory, FriendlyByteBuf extraData) {
		this(containerId, plrInventory, ContainerLevelAccess.NULL);

		this.signalDesignation = extraData.readUtf();
	}

	public MenuTest(int containerId, Inventory plrInventory, ContainerLevelAccess pLevel) {
		super(SwMenus.MENU_TEST.get(), containerId);

		this.access = pLevel;
	}

	public String getSignalDesignation() {
		return signalDesignation;
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
