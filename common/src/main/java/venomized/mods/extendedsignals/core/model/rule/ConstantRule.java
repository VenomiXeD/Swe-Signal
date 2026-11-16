package venomized.mods.extendedsignals.core.model.rule;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

public class ConstantRule extends Rule<ConstantRule> {
    @Getter
    @Setter
    private boolean constant;

    public ConstantRule(RuleType<ConstantRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @param context
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        return constant;
    }

    /**
     * @param tag
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        super.writeToNbt(tag);
        tag.putBoolean("constant", isConstant());
    }

    /**
     * @param tag
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        super.readFromNbt(tag);
        setConstant(tag.getBoolean("constant"));
    }
}
