package venomized.mods.extendedsignals.core.signalling.rule;

import net.minecraft.util.parsing.packrat.Rule;

@FunctionalInterface
public interface IRuleFactory<T extends IRule> {
    T create();
}