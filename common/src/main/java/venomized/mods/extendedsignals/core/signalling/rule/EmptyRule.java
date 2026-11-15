package venomized.mods.extendedsignals.core.signalling.rule;

public class EmptyRule implements IRule {
    /**
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        return false;
    }
}
