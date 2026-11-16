package venomized.mods.extendedsignals.core.model.rule;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

public class CartCountRule extends ComparisonRule<CartCountRule> {
    @Getter
    @Setter
    private int cartCount;

    public CartCountRule(RuleType<CartCountRule> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @param context
     * @return
     */
    @Override
    public boolean evaluate(IRuleEvaluationContext context) {
        return getComparisonOperator().compare(cartCount, context.trainData().carriageCount());
    }

    /**
     * @param tag
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        super.writeToNbt(tag);
        tag.putInt("cart_count", getCartCount());
    }

    /**
     * @param tag
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        super.readFromNbt(tag);
        setCartCount(tag.getInt("cart_count"));
    }
}
