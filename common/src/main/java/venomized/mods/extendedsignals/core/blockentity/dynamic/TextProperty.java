package venomized.mods.extendedsignals.core.blockentity.dynamic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class TextProperty extends Property<String> {
    public TextProperty(Component label, Component tooltipLabel) {
        this("", label, tooltipLabel);
    }

    public TextProperty(String defaultValue, Component label, Component tooltipLabel) {
        super(defaultValue, label, tooltipLabel);
    }

    /**
     * @param tag
     */
    @Override
    public void deserializeValue(CompoundTag tag) {
        setValue(tag.getString("string_value"));
    }

    /**
     * @return
     */
    @Override
    public CompoundTag serializeValue() {
        CompoundTag tag = new CompoundTag();
        tag.putString("string_value", getValue());

        return tag;
    }
}
