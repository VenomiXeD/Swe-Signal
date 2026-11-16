package venomized.mods.extendedsignals.core.client.ui.rule;

import venomized.mods.extendedsignals.core.model.rule.IRule;

public interface IRuleInterfaceBuilder<T extends IRule> {
    void build(T ruleInstance);
}
