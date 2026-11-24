package venomized.mods.extendedsignals.core.model.rule;

import java.util.List;

public interface ICompositeRule {
    List<IRule> children();
}
