package venomized.mods.extendedsignals.core.signalling.rule;

public class OrRule extends ComposableRule {
    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        for (IRule rule : rules) {
            if (rule.evaluate(context)) {
                return true;
            }
        }
        return false;
    }
}
