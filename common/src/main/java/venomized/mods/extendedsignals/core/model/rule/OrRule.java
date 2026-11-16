package venomized.mods.extendedsignals.core.model.rule;

public class OrRule extends ComposableRule<OrRule> {
    public OrRule(RuleType<OrRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        for (IRule rule : getChildren()) {
            if (rule.evaluate(context)) {
                return true;
            }
        }
        return false;
    }
}
