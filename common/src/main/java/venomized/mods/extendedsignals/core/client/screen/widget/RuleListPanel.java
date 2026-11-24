package venomized.mods.extendedsignals.core.client.screen.widget;

import com.mojang.blaze3d.vertex.Tesselator;
import com.simibubi.create.foundation.item.ItemSlots;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.model.rule.AndRule;
import venomized.mods.extendedsignals.core.model.rule.IRule;
import venomized.mods.extendedsignals.core.model.rule.Rule;
import venomized.mods.extendedsignals.core.model.rule.Rules;

import java.util.List;

public class RuleListPanel extends ScrollPanel {
    private final IRule root;

    @Getter
    private final List<Renderable> elements = new ReferenceArrayList<>();

    public RuleListPanel(IRule rootRule, Minecraft client, int width, int height, int top, int left) {
        super(client, width, height, top, left);
        this.root = Rules.RULE_AND.create();
        ((AndRule) root).addRule(Rules.RULE_PATH_LABEL.create());
        ((AndRule) root).addRule(Rules.RULE_PATH_LABEL.create());

        initializeEditor();
    }

    private void initializeEditor() {
        List<Rules.RuleGraphEntry> flattenedView = Rules.flatten(root);
        flattenedView.forEach(entry -> {
            EditBox b;
            elements.add(b = new EditBox(
                    Minecraft.getInstance().font, right - left, 50, Component.empty())
            );

            b.setValue(entry.ruleInstance().id().toString());
        });
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
