package venomized.mc.mods.swsignals.client.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class ScreenTest extends AbstractContainerScreen<MenuTest> {
	private EditBox editBox;

	public ScreenTest(MenuTest pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);

		// this.width = Minecraft.getInstance().screen.width/2;
		// this.height = Minecraft.getInstance().screen.height/2;

		this.editBox = this.addRenderableWidget(
				new EditBox(
						Minecraft.getInstance().font,
						128,
						128	,
						128,
						20,
						Component.nullToEmpty("")
				)
		);

		this.editBox.setValue(pMenu.getSignalDesignation());
	}

	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
	}

	/**
	 * Renders the graphical user interface (GUI) element.
	 *
	 * @param pGuiGraphics the GuiGraphics object used for rendering.
	 * @param pMouseX      the x-coordinate of the mouse cursor.
	 * @param pMouseY      the y-coordinate of the mouse cursor.
	 * @param pPartialTick the partial tick time.
	 */
	@Override
	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
		this.editBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
	}

	@Override
	protected void containerTick() {
		this.editBox.tick();
	}

	@Override
	protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
		super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
	}


}
