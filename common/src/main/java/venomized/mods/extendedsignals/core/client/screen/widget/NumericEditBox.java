package venomized.mods.extendedsignals.core.client.screen.widget;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalDouble;

public class NumericEditBox extends EditBox {
    public NumericEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        this(pFont, pX, pY, pWidth, pHeight, null, pMessage);
    }

    /**
     * @param pFont
     * @param pX
     * @param pY
     * @param pWidth
     * @param pHeight
     * @param pEditBox
     * @param pMessage
     */
    public NumericEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, @Nullable EditBox pEditBox, Component pMessage) {
        super(pFont, pX, pY, pWidth, pHeight, pEditBox, pMessage);
        this.setFilter(this::numericFilter);
    }

    public static OptionalDouble parseDouble(String s) {
        try {
            return OptionalDouble.of(Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return OptionalDouble.empty();
        }
    }

    private boolean numericFilter(String s) {
        return s.isEmpty() || s.matches("^-?") || parseDouble(s).isPresent();
    }

    public OptionalDouble getNumericValue() {
        return parseDouble(getValue());
    }
}
