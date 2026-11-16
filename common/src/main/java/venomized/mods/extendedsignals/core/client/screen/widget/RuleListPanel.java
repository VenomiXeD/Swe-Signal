package venomized.mods.extendedsignals.core.client.screen.widget;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import venomized.mods.extendedsignals.core.ExtendedSignals;

public class RuleListPanel extends ScrollPanel {
    public RuleListPanel(Minecraft client, int width, int height, int top, int left) {
        super(client, width, height, top, left);
    }

    /**
     * @return
     */
    @Override
    protected int getContentHeight() {
        return 2560;
    }

    /**
     * @param guiGraphics
     * @param entryRight
     * @param relativeY
     * @param tess
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        guiGraphics.blit(InventoryScreen.INVENTORY_LOCATION, left, top, 0, 0, 256, 900, 256, 176);
    }

    /**
     * @return
     */
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    /**
     * @param narrationElementOutput the output to update with narration information.
     */
    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }
}
