package venomized.mods.extendedsignals.core.model.rule;

public class NotRule extends Rule<NotRule> {
    private IRule rule;

    public NotRule(RuleType<NotRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        if (rule == null)
            return false;

        return !rule.evaluate(context);
    }
}
