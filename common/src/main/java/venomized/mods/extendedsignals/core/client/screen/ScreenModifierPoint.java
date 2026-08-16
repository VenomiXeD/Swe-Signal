package venomized.mods.extendedsignals.core.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import venomized.mods.extendedsignals.core.blockentity.dynamic.BoolProperty;
import venomized.mods.extendedsignals.core.blockentity.dynamic.Property;
import venomized.mods.extendedsignals.core.blockentity.dynamic.TextProperty;
import venomized.mods.extendedsignals.core.menu.MenuModifierPoint;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundConfigurePointPacket;

import java.util.Map;
import java.util.function.Consumer;

public class ScreenModifierPoint extends AbstractContainerScreen<MenuModifierPoint> {
    public ScreenModifierPoint(MenuModifierPoint menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    /**
     *
     */
    @Override
    protected void init() {
        imageWidth = width;
        imageHeight = height;
        super.init();

        leftPos = 0;
        topPos = 0;

        configurePropertyWidgets();
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> void configurePropertyWidgets() {
        int OFFSET_TOP_POS = 0;

        final int WIDGET_WIDTH_MAX = 600;
        final int WIDGET_HEIGHT_MAX = 15;
        final int WIDGET_HEIGHT_PAD = 5;

        for (Map.Entry<String, Property<?>> property : menu.getTargetBlockEntity().getProperties().getProperties().entrySet()) {
            LayoutBuilder.WidgetLayout pos;
            T widget;

            if (property.getValue() instanceof BoolProperty boolProperty) {
                pos = new LayoutBuilder()
                        .anchor(0, 0)
                        .scale(.5f, 1f / 12f)
                        .size(WIDGET_WIDTH_MAX, WIDGET_HEIGHT_MAX)
                        .offset(0, OFFSET_TOP_POS)
                        .build(this);

                addRenderableWidget(
                        Checkbox.builder(boolProperty.getLabel(), font)
                                .tooltip(Tooltip.create(boolProperty.getTooltipLabel()))
                                .pos(pos.x(), pos.y())
                                // .maxWidth(pos.w())
                                .selected(boolProperty.getValue())
                                .onValueChange(checkboxChanged(property.getKey()))
                                .build()
                );
            } else if (property.getValue() instanceof TextProperty textProperty) {
                pos = new LayoutBuilder()
                        .anchor(0.5f, 0)
                        .scale(.5f, 1f / 12f)
                        .size(WIDGET_WIDTH_MAX, WIDGET_HEIGHT_MAX)
                        .offset(0, OFFSET_TOP_POS)
                        .build(this);


                EditBox editBox = addRenderableWidget(
                        new EditBox(font, pos.x(), pos.y(), pos.w(), pos.h(), textProperty.getLabel())
                );

                editBox.setValue(textProperty.getValue());
                editBox.setTooltip(Tooltip.create(textProperty.getTooltipLabel()));
                editBox.setResponder(textChanged(property.getKey()));
            }


            OFFSET_TOP_POS += WIDGET_HEIGHT_MAX + WIDGET_HEIGHT_PAD;
        }
    }

    private Consumer<String> textChanged(String key) {
        return (s) -> {
            ((TextProperty) menu.getTargetBlockEntity().getProperties().getProperty(key)).setValue(s);
            propertyChanged();
        };
    }

    private Checkbox.OnValueChange checkboxChanged(String key) {
        return (c, v) -> {
            ((BoolProperty) menu.getTargetBlockEntity().getProperties().getProperty(key)).setValue(v);
            propertyChanged();
        };
    }

    public void propertyChanged() {
        PacketDistributor.sendToServer(new ServerBoundConfigurePointPacket(
                menu.getTargetBlockEntity().getBlockPos(),
                menu.getTargetBlockEntity().getProperties().toNBT()
        ));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == Minecraft.getInstance().options.keyInventory.getKey().getValue())
            if (this.getFocused() instanceof EditBox)
                return true;

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    /**
     * @param guiGraphics
     * @param partialTick
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }
}
