package venomized.mods.extendedsignals.core.signalling.rule;

import net.minecraft.util.parsing.packrat.Rule;

public record RuleType<T extends IRule>(IRuleFactory<T> factory) {
}
