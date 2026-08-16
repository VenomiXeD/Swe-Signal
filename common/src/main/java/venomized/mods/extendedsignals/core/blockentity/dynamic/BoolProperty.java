package venomized.mods.extendedsignals.core.blockentity.dynamic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;


public class BoolProperty extends Property<Boolean> {
    public BoolProperty(Component label, Component tooltipLabel) {
        this(false, label, tooltipLabel);
    }

    public BoolProperty(boolean defaultValue, Component label, Component tooltipLabel) {
        super(defaultValue, label, tooltipLabel);
    }

    /**
     * @param tag
     */
    @Override
    public void deserializeValue(CompoundTag tag) {
        setValue(tag.getBoolean("bool_value"));
    }

    /**
     * @return
     */
    @Override
    public CompoundTag serializeValue() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("bool_value", getValue());
        return tag;
    }
}
