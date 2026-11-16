package venomized.mods.extendedsignals.core.blockentity;

import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.model.rule.IRule;

public interface IConfigurableRuleBlockEntity {
    @Nullable
    IRule getRoot();
}
