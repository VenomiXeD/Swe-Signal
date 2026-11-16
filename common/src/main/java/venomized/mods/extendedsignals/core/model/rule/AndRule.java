package venomized.mods.extendedsignals.core.model.rule;

public class AndRule extends ComposableRule<AndRule> {
    public AndRule(RuleType<AndRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        for (IRule rule : getChildren()) {
            if (!rule.evaluate(context)) {
                return false;
            }
        }
        return true;
    }
}
