package venomized.mods.extendedsignals.core.model.rule;

public class EmptyRule extends Rule<EmptyRule> {
    public EmptyRule(RuleType<EmptyRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        return false;
    }
}
