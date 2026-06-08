package venomized.mods.extendedsignals.core.client.screen;

import net.minecraft.client.gui.screens.Screen;

public class LayoutBuilder {
    public record WidgetLayout(int x, int y, int w, int h) {
    }

    private double scaleX;
    private double scaleY;

    private int width = 0;
    private int height = 0;

    private double anchorX = 0.5;
    private double anchorY = 0.5;

    private int offsetX = 0;
    private int offsetY = 0;

    public LayoutBuilder scale(double x, double y) {
        this.scaleX = x;
        this.scaleY = y;
        return this;
    }

    public LayoutBuilder size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public LayoutBuilder anchor(double x, double y) {
        this.anchorX = x;
        this.anchorY = y;
        return this;
    }

    public LayoutBuilder offset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
        return this;
    }

    public WidgetLayout build(Screen screen) {
        int x = (int) (screen.width * scaleX);
        int y = (int) (screen.height * scaleY);

        x -= (int) (width * anchorX);
        y -= (int) (height * anchorY);

        x += offsetX;
        y += offsetY;

        return new WidgetLayout(x, y, width, height);
    }
}