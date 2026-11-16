package venomized.mods.extendedsignals.core.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableRuleBlockEntity;
import venomized.mods.extendedsignals.core.client.screen.widget.RuleListPanel;
import venomized.mods.extendedsignals.core.client.ui.rule.IRuleInterfaceBuilder;

@OnlyIn(Dist.CLIENT)
public class ScreenRuleEditor extends Screen {
    private final Minecraft mc = Minecraft.getInstance();

    IConfigurableRuleBlockEntity ruleBlockEntity;

    public ScreenRuleEditor(IConfigurableRuleBlockEntity ruleBlockEntity) {
        super(Component.literal("test"));

        this.ruleBlockEntity = ruleBlockEntity;
    }

    @Override
    protected void init() {
        super.init();

        int w = 600 / mc.options.guiScale().get();
        int h = 600 / mc.options.guiScale().get();
        int left = (width - w) / 2;
        int top = (height - h) / 2;

        addRenderableWidget(new RuleListPanel(mc, w, h, top, left));
    }
}
