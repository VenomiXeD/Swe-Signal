import net.minecraft.nbt.CompoundTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import venomized.mods.extendedsignals.core.model.rule.AndRule;
import venomized.mods.extendedsignals.core.model.rule.Rules;
import venomized.mods.extendedsignals.core.model.rule.IRule;

public class RuleTests {
    @Test
    void test_composable_rules_serialize_count() {
        IRule constantRule0 = Rules.RULE_CONSTANT.create();
        IRule constantRule1 = Rules.RULE_CONSTANT.create();

        AndRule originalAnd = Rules.RULE_AND.create();
        originalAnd.addRule(constantRule0);
        originalAnd.addRule(constantRule1);

        CompoundTag serialized = Rules.serialize(originalAnd);
        IRule deserialized = Rules.deserialize(serialized);

        Assertions.assertInstanceOf(AndRule.class, deserialized);
        Assertions.assertEquals(originalAnd.getChildren().size(), ((AndRule) deserialized).getChildren().size());
    }
}
