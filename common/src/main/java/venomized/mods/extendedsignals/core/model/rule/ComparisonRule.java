package venomized.mods.extendedsignals.core.model.rule;

import lombok.Getter;
import lombok.Setter;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.model.ComparisonOperator;

public abstract class ComparisonRule<T extends IRule> extends Rule<T> {
    @Getter
    @Setter
    private ComparisonOperator comparisonOperator = ComparisonOperator.EQUAL_TO;

    public ComparisonRule(RuleType<T> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @param tag
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        super.writeToNbt(tag);
        NBTHelper.writeEnum(tag, "comparison_operator", getComparisonOperator());
    }

    /**
     * @param tag
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        super.readFromNbt(tag);
        setComparisonOperator(NBTHelper.readEnum(tag, "comparison_operator", ComparisonOperator.class));
    }
}
