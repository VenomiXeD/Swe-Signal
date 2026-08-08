package venomized.mods.extendedsignals.core.util;

import java.util.Objects;
import java.util.function.BiConsumer;

public class TrackedValue<T> {
    private final BiConsumer<T, T> changeCallback;

    private T fromValue;
    private T toValue;

    public TrackedValue(T initial) {
        this(initial, null);
    }

    public TrackedValue(T initial, BiConsumer<T, T> changeCallback) {
        this.fromValue = initial;
        this.toValue = initial;
        this.changeCallback = changeCallback;
    }

    public boolean change(T value) {
        if (Objects.equals(toValue, value))
            return false;

        fromValue = toValue;
        toValue = value;

        if (changeCallback != null) {
            changeCallback.accept(fromValue, toValue);
        }

        return true;
    }

    public T fromValue() {
        return fromValue;
    }

    public T toValue() {
        return toValue;
    }

    public T value() {
        return toValue;
    }
}