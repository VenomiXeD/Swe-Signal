package venomized.mods.extendedsignals.core.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import venomized.mods.extendedsignals.core.client.screen.widget.NumericEditBox;
import venomized.mods.extendedsignals.core.menu.MenuModelConfig;
import venomized.mods.extendedsignals.core.network.ExtendedSignalsNetworking;
import venomized.mods.extendedsignals.core.network.packets.ServerBoundModelConfigurePacket;

public class ScreenModelConfig extends AbstractContainerScreen<MenuModelConfig> {

    private static final int WIDGET_WIDTH = 180;
    private static final int WIDGET_HEIGHT = 12;


    private final NumericEditBox[] locOffsetEditBoxes;
    private final ForgeSlider[] locOffsetSliders;
    private final double[] locOffsetValues;

    private final NumericEditBox[] gloOffsetEditBoxes;
    private final ForgeSlider[] gloOffsetSliders;
    private final double[] gloOffsetValues;

    private final NumericEditBox[] orientationEditBoxes;
    private final ForgeSlider[] orientationSliders;
    private final double[] orientationValues;

    public ScreenModelConfig(MenuModelConfig pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        locOffsetEditBoxes = new NumericEditBox[3];
        locOffsetSliders = new ForgeSlider[3];
        locOffsetValues = new double[]{
                pMenu.getReferenceBlockEntity().getXLocOffset() * 16d,
                pMenu.getReferenceBlockEntity().getYLocOffset() * 16d,
                pMenu.getReferenceBlockEntity().getZLocOffset() * 16d
        };

        gloOffsetEditBoxes = new NumericEditBox[3];
        gloOffsetSliders = new ForgeSlider[3];
        gloOffsetValues = new double[]{
                pMenu.getReferenceBlockEntity().getXGblOffset() * 16d,
                pMenu.getReferenceBlockEntity().getYGblOffset() * 16d,
                pMenu.getReferenceBlockEntity().getZGblOffset() * 16d
        };

        orientationEditBoxes = new NumericEditBox[3];
        orientationSliders = new ForgeSlider[3];
        orientationValues = new double[]{
                pMenu.getReferenceBlockEntity().getXOrientation(),
                pMenu.getReferenceBlockEntity().getYOrientation(),
                pMenu.getReferenceBlockEntity().getZOrientation(),
        };
        // this.inventoryLabelY = 10000;
    }


    /**
     *
     */
    @Override
    protected void init() {
        this.imageWidth = width;
        this.imageHeight = height;

        super.init();

        this.leftPos = 0;
        this.topPos = 0;

        configureWidgets();
    }

    private void configureWidgets() {
        configureLocalOffsetWidgets();
        configureGlobalOffsetWidgets();
        configureRotationWidgets();
        updateEditBoxValues();
    }

    private void configureGlobalOffsetWidgets() {
        LayoutBuilder.WidgetLayout pos;

        final int TOP_OFFSET = 0;
        final int WIDGET_PADDING = 15;
        //region gbl x
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 3f / 6f)
                .offset(10, -WIDGET_PADDING * 2 + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(gloOffsetSliders[0] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.gblx"), Component.empty(),
                        -32, 32, gloOffsetValues[0], 1d / 2d, 2,
                        true
                )
        );
        gloOffsetSliders[0].setFGColor(0xFF0000);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 3f / 6f)
                .offset(10, -WIDGET_PADDING * 3 + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(gloOffsetEditBoxes[0] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.gblx")
                )
        );
        gloOffsetEditBoxes[0].setResponder(this::editBoxChanged);
        //endregion gbl x

        //region gbl y
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 3f / 6f)
                .offset(10, TOP_OFFSET)
                .build(this);
        this.addRenderableWidget(gloOffsetSliders[1] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.gbly"), Component.empty(),
                        -32, 32, gloOffsetValues[1], 1d / 2d, 2,
                        true
                )
        );
        gloOffsetSliders[1].setFGColor(0x00FF00);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 3f / 6f)
                .offset(10, -WIDGET_PADDING + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(gloOffsetEditBoxes[1] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.gbly")
                )
        );
        gloOffsetEditBoxes[1].setResponder(this::editBoxChanged);
        //endregion gbl y

        //region gbl z
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 3f / 6f)
                .offset(10, WIDGET_PADDING * 2 + TOP_OFFSET)
                .build(this);
        this.addRenderableWidget(gloOffsetSliders[2] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.gblz"), Component.empty(),
                        -32, 32, gloOffsetValues[2], 1d / 2d, 2,
                        true
                )
        );
        gloOffsetSliders[2].setFGColor(0x0000FF);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 3f / 6f)
                .offset(10, WIDGET_PADDING + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(gloOffsetEditBoxes[2] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.gblz")
                )
        );
        gloOffsetEditBoxes[2].setResponder(this::editBoxChanged);
        //endregion gbl z
    }

    private void configureLocalOffsetWidgets() {
        LayoutBuilder.WidgetLayout pos;

        final int TOP_OFFSET = 0;
        final int WIDGET_PADDING = 15;
        //region loc x
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 1f / 6f)
                .offset(10, -WIDGET_PADDING * 2 + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(locOffsetSliders[0] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.locx"), Component.empty(),
                        -32, 32, locOffsetValues[0], 1d / 2d, 2,
                        true
                )
        );
        locOffsetSliders[0].setFGColor(0xFF0000);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 1f / 6f)
                .offset(10, -WIDGET_PADDING * 3 + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(locOffsetEditBoxes[0] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.locx")
                )
        );
        locOffsetEditBoxes[0].setResponder(this::editBoxChanged);
        //endregion loc x


        //region loc y
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 1f / 6f)
                .offset(10, TOP_OFFSET)
                .build(this);
        this.addRenderableWidget(locOffsetSliders[1] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.locy"), Component.empty(),
                        -32, 32, locOffsetValues[1], 1d / 2d, 2,
                        true
                )
        );
        locOffsetSliders[1].setFGColor(0x00FF00);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 1f / 6f)
                .offset(10, -WIDGET_PADDING + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(locOffsetEditBoxes[1] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.locy")
                )
        );
        locOffsetEditBoxes[1].setResponder(this::editBoxChanged);
        //endregion loc y

        //region loc z
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 1f / 6f)
                .offset(10, WIDGET_PADDING * 2 + TOP_OFFSET)
                .build(this);
        this.addRenderableWidget(locOffsetSliders[2] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.locz"), Component.empty(),
                        -32, 32, locOffsetValues[2], 1d / 2d, 2,
                        true
                )
        );
        locOffsetSliders[2].setFGColor(0x0000FF);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 1f / 6f)
                .offset(10, WIDGET_PADDING + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(locOffsetEditBoxes[2] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.locz")
                )
        );
        locOffsetEditBoxes[2].setResponder(this::editBoxChanged);
        //endregion loc z
    }

    private void configureRotationWidgets() {
        LayoutBuilder.WidgetLayout pos;

        final int TOP_OFFSET = 0;
        final int WIDGET_PADDING = 15;
        //region loc x
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 5f / 6f)
                .offset(10, -WIDGET_PADDING * 2 + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(orientationSliders[0] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.rotx"), Component.empty(),
                        0, 360, orientationValues[0], 1d / 2d, 2,
                        true
                )
        );
        orientationSliders[0].setFGColor(0xFF0000);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 5f / 6f)
                .offset(10, -WIDGET_PADDING * 3 + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(orientationEditBoxes[0] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.rotx")
                )
        );
        orientationEditBoxes[0].setResponder(this::editBoxChanged);
        //endregion loc x


        //region loc y
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 5f / 6f)
                .offset(10, TOP_OFFSET)
                .build(this);
        this.addRenderableWidget(orientationSliders[1] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.roty"), Component.empty(),
                        0, 360, orientationValues[1], 1d / 2d, 2,
                        true
                )
        );
        orientationSliders[1].setFGColor(0x00FF00);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 5f / 6f)
                .offset(10, -WIDGET_PADDING + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(orientationEditBoxes[1] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.roty")
                )
        );
        orientationEditBoxes[1].setResponder(this::editBoxChanged);
        //endregion loc y

        //region loc z
        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 5f / 6f)
                .offset(10, WIDGET_PADDING * 2 + TOP_OFFSET)
                .build(this);
        this.addRenderableWidget(orientationSliders[2] = new ForgeSlider(
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.slider.rotz"), Component.empty(),
                        -180, 180, orientationValues[2], 1d / 2d, 2,
                        true
                )
        );
        orientationSliders[2].setFGColor(0x0000FF);

        pos = new LayoutBuilder()
                .anchor(0, .5)
                .size(WIDGET_WIDTH, WIDGET_HEIGHT)
                .scale(0, 5f / 6f)
                .offset(10, WIDGET_PADDING + TOP_OFFSET)
                .build(this);

        this.addRenderableWidget(orientationEditBoxes[2] = new NumericEditBox(font,
                        pos.x(), pos.y(), pos.w(), pos.h(),
                        Component.translatable("screens.extended_signals.modelconfig.box.rotz")
                )
        );
        orientationEditBoxes[2].setResponder(this::editBoxChanged);
        //endregion loc z
    }

    private void editBoxChanged(String s) {
        for (int i = 0; i < 3; i++) {
            if (locOffsetEditBoxes[i].getNumericValue().isPresent() && locOffsetEditBoxes[i].isFocused()) {
                locOffsetValues[i] = locOffsetEditBoxes[i].getNumericValue().getAsDouble();
                updateModelTranslation();
                updateSliderValues();
            }
            if (gloOffsetEditBoxes[i].getNumericValue().isPresent() && gloOffsetEditBoxes[i].isFocused()) {
                gloOffsetValues[i] = gloOffsetEditBoxes[i].getNumericValue().getAsDouble();
                updateModelTranslation();
                updateSliderValues();
            }
            if (orientationEditBoxes[i].getNumericValue().isPresent() && orientationEditBoxes[i].isFocused()) {
                orientationValues[i] = orientationEditBoxes[i].getNumericValue().getAsDouble();
                updateModelTranslation();
                updateSliderValues();
            }
        }
    }

    /**
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pButton the button that is being dragged.
     * @param pDragX  the X distance of the drag.
     * @param pDragY  the Y distance of the drag.
     * @return
     */
    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        for (int i = 0; i < 3; i++) {
            if (locOffsetSliders[i].isFocused())
                return locOffsetSliders[i].mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);

            if (gloOffsetSliders[i].isFocused())
                return gloOffsetSliders[i].mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);

            if (orientationSliders[i].isFocused())
                return orientationSliders[i].mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }

        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    /**
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pButton the button that was released.
     * @return
     */
    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        for (int i = 0; i < 3; i++) {
            gloOffsetSliders[i].setFocused(false);
            orientationSliders[i].setFocused(false);
            locOffsetSliders[i].setFocused(false);
        }

        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    /**
     * @param pGuiGraphics the GuiGraphics object used for rendering.
     * @param pMouseX      the x-coordinate of the mouse cursor.
     * @param pMouseY      the y-coordinate of the mouse cursor.
     * @param pPartialTick the partial tick time.
     */
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    /**
     *
     */
    @Override
    protected void containerTick() {
        for (int i = 0; i < 3; i++) {
            if (locOffsetSliders[i].getValue() != locOffsetValues[i] &&
                    locOffsetSliders[i].isFocused()) {
                locOffsetValues[i] = locOffsetSliders[i].getValue();
                updateModelTranslation();
                updateEditBoxValues();
            }

            if (gloOffsetSliders[i].getValue() != gloOffsetValues[i] &&
                    gloOffsetSliders[i].isFocused()) {
                gloOffsetValues[i] = gloOffsetSliders[i].getValue();
                updateModelTranslation();
                updateEditBoxValues();
            }

            if (orientationSliders[i].getValue() != orientationValues[i] &&
                    orientationSliders[i].isFocused()) {
                orientationValues[i] = orientationSliders[i].getValue();
                updateModelTranslation();
                updateEditBoxValues();
            }
        }
    }

    private void updateEditBoxValues() {
        for (int i = 0; i < 3; i++) {
            this.locOffsetEditBoxes[i].setValue(
                    "%.2f".formatted(locOffsetValues[i])
            );

            this.gloOffsetEditBoxes[i].setValue(
                    "%.2f".formatted(gloOffsetValues[i])
            );

            this.orientationEditBoxes[i].setValue(
                    "%.2f".formatted(orientationValues[i])
            );
        }
    }

    private void updateSliderValues() {
        for (int i = 0; i < 3; i++) {
            this.locOffsetSliders[i].setValue(
                    locOffsetValues[i]
            );

            this.gloOffsetSliders[i].setValue(
                    gloOffsetValues[i]
            );

            this.orientationSliders[i].setValue(
                    orientationValues[i]
            );
        }
    }

    private void updateModelTranslation() {
        ExtendedSignalsNetworking.CHANNEL
                .sendToServer(new ServerBoundModelConfigurePacket(
                        menu.getBlockEntityPosition(),
                        new Vec3(
                                locOffsetValues[0] / 16d,
                                locOffsetValues[1] / 16d,
                                locOffsetValues[2] / 16d
                        ),
                        new Vec3(
                                gloOffsetValues[0] / 16d,
                                gloOffsetValues[1] / 16d,
                                gloOffsetValues[2] / 16d
                        ),
                        new Vec3(
                                orientationValues[0],
                                orientationValues[1],
                                orientationValues[2]
                        )
                ));
        // locOffsetEditBoxes[0].setValue(String.valueOf(locOffsetValues[0]));
    }

    /**
     * @param pGuiGraphics
     * @param pPartialTick
     * @param pMouseX
     * @param pMouseY
     */
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        // pGuiGraphics.fill(
        //         leftPos, topPos,
        //         leftPos + imageWidth,
        //         topPos + imageHeight,
        //         0xAA000000
        // );
    }

    /**
     * @param pGuiGraphics
     * @param pMouseX
     * @param pMouseY
     */
    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
    }
}
