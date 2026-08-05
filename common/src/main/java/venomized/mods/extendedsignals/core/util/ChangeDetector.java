package venomized.mods.extendedsignals.core.util;

import java.util.function.BiConsumer;

public class ChangeDetector<T> {
    private T oldValue;
    private final BiConsumer<T, T> changeCallback;

    public ChangeDetector(T initial) {
        this(initial, null);
    }

    public ChangeDetector(T initial, BiConsumer<T, T> changeCallback) {
        oldValue = initial;
        this.changeCallback = changeCallback;
    }

    public boolean change(T changedValue) {
        if (oldValue.equals(changedValue))
            return false;
        oldValue = changedValue;

        if (changeCallback != null) {
            changeCallback.accept(changedValue, oldValue);
        }
        return true;
    }

    public T oldValue() {
        return oldValue;
    }
}
