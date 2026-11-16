package venomized.mods.extendedsignals.core.model;

import net.neoforged.neoforge.common.TranslatableEnum;
import org.apache.commons.lang3.function.ToBooleanBiFunction;

import java.util.Objects;

public enum ComparisonOperator implements ToBooleanBiFunction<Number, Number>, TranslatableEnum {
    EQUAL_TO,
    NOT_EQUAL_TO,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL_TO,
    LESS_THAN,
    LESS_THAN_OR_EQUAL_TO,
    ;

    /**
     * @param number  the first function argument.
     * @param number2 the second function argument.
     * @return
     */
    @Override
    public boolean applyAsBoolean(Number number, Number number2) {
        Objects.requireNonNull(number);
        Objects.requireNonNull(number2);
        return switch (this) {
            case EQUAL_TO -> Objects.equals(number, number2);
            case NOT_EQUAL_TO -> !Objects.equals(number, number2);
            case GREATER_THAN -> number2.doubleValue() > number.doubleValue();
            case GREATER_THAN_OR_EQUAL_TO -> number2.doubleValue() >= number.doubleValue();
            case LESS_THAN -> number2.doubleValue() < number.doubleValue();
            case LESS_THAN_OR_EQUAL_TO -> number2.doubleValue() <= number.doubleValue();
        };
    }

    public boolean compare(Number a, Number b) {
        return applyAsBoolean(a, b);
    }
}
