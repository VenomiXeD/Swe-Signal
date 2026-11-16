package venomized.mods.extendedsignals.core.model.rule;

import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.client.ui.rule.IRuleInterfaceBuilder;

import java.util.function.Supplier;

public record RuleType<T extends IRule>(ResourceLocation id, IRuleFactory<T> ruleFactory) implements Supplier<T> {
    public T create() {
        return ruleFactory.create(this);
    }

    /**
     * @return
     */
    @Override
    public T get() {
        return create();
    }
}
