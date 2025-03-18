package venomized.mc.mods.swsignals.client.ui;

import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import venomized.mc.mods.swsignals.SwSignal;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ScreenTest extends AbstractContainerScreen<MenuTest> {
	private static final ResourceLocation UI_TEX = SwSignal.modLoc("textures/ui/se_signalbox.png");

	private final MenuTest menu;

	private EditBox editBox;
	private net.minecraft.client.gui.components.CycleButton<String> optionsList;


	public ScreenTest(MenuTest pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
		this.menu = pMenu;

		this.imageWidth = 248;
		this.imageHeight = 166;

		// this.width = Minecraft.getInstance().screen.width/2;
		// this.height = Minecraft.getInstance().screen.height/2;



	}

	@Override
	protected void init() {
		super.init();
		this.editBox = this.addRenderableWidget(
				new EditBox(
						Minecraft.getInstance().font,
						this.leftPos + 13,
						this.topPos + 22,
						120,
						22,
						Component.nullToEmpty("")
				)
		);

		this.editBox.setBordered(false);

		optionsList = this.addRenderableWidget(
				CycleButton.<String>builder(Component::literal)
						.withValues(
								"Hello",
								"World",
								"Value 2"
						)
						.create(this.leftPos + 10,this.topPos + 50,120, 16, Component.literal("option"),
								(ins,newValue)->System.out.println(newValue)
						)
		);

		this.editBox.setHint(Component.literal("sample text"));
	}

	@Override
	public void renderBackground(GuiGraphics pGuiGraphics) {
		System.out.println("render background");
		pGuiGraphics.blit(
				UI_TEX,
				this.leftPos,
				this.topPos,
				0,
				0,
				this.imageWidth,
				this.imageHeight
		);
	}





	@Override
	protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
			renderBackground(pGuiGraphics);
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
		super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
		this.editBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
		this.optionsList.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
		// this.options.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
	}

	@Override
	protected void containerTick() {
		this.editBox.tick();
		// this.options.ti
		// System.out.println(this.optionsList.getValue());

		super.containerTick();
	}

	@Override
	protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
		// super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	/**
	 * Called when a keyboard key is pressed within the GUI element.
	 * <p>
	 *
	 * @param pKeyCode   the key code of the pressed key.
	 * @param pScanCode  the scan code of the pressed key.
	 * @param pModifiers the keyboard modifiers.
	 * @return {@code true} if the event is consumed, {@code false} otherwise.
	 */
	@Override
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		if (this.editBox.isFocused()) {
			return this.editBox.keyPressed(pKeyCode, pScanCode, pModifiers);
		}
		return super.keyPressed(pKeyCode,pScanCode,pModifiers);
	}
}
