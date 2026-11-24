package venomized.mods.extendedsignals.core.model.rule;

import java.util.List;

public interface ICompositeRule extends IRule {
    List<IRule> children();
}
