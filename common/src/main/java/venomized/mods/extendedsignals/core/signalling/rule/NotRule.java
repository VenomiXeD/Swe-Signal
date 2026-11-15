package venomized.mods.extendedsignals.core.signalling.rule;

public class NotRule implements IRule {
    private final IRule rule = new EmptyRule();

    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        return !rule.evaluate(context);
    }
}
