package venomized.mods.extendedsignals.core.blockentity.dynamic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;


@RequiredArgsConstructor
public abstract class Property<T> {
    @Setter
    protected T value;

    public T getValue() {
        if (value == null)
            value = getDefaultValue();
        return value;
    }

    @Getter
    private final T defaultValue;

    @Getter
    protected final Component label;
    @Getter
    protected final Component tooltipLabel;

    public abstract void deserializeValue(CompoundTag tag);

    public abstract CompoundTag serializeValue();
}
