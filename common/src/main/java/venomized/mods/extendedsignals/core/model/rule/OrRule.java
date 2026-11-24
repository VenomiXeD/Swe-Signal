package venomized.mods.extendedsignals.core.model.rule;

public class OrRule extends CompositeRule<OrRule> {
    public OrRule(RuleType<OrRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        for (IRule rule : children()) {
            if (rule.evaluate(context)) {
                return true;
            }
        }
        return false;
    }
}
