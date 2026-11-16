package venomized.mods.extendedsignals.core.model.rule;

import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.client.ui.rule.IRuleInterfaceBuilder;

public abstract class Rule<S extends IRule> implements IRule {
    private final RuleType<S> ruleType;

    /**
     * @return
     */
    @Override
    public ResourceLocation id() {
        return ruleType.id();
    }

    public Rule(RuleType<S> selfRuleType) {
        ruleType = selfRuleType;
    }
}
