package venomized.mods.extendedsignals.core.model.rule;

public class AndRule extends CompositeRule<AndRule> {
    public AndRule(RuleType<AndRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        for (IRule rule : children()) {
            if (!rule.evaluate(context)) {
                return false;
            }
        }
        return true;
    }
}
