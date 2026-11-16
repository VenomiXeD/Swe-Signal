package venomized.mods.extendedsignals.core.model.rule;

import java.util.function.Function;

@FunctionalInterface
public interface IRuleFactory<T extends IRule> extends Function<RuleType<T>, T> {
    /**
     * @param selfRuleType the function argument
     * @return
     */
    @Override
    default T apply(RuleType<T> selfRuleType) {
        return create(selfRuleType);
    }

    T create(RuleType<T> selfRuleType);
}