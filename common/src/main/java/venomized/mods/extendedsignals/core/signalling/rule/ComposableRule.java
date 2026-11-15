package venomized.mods.extendedsignals.core.signalling.rule;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;

import java.util.List;

public abstract class ComposableRule implements IRule {
    protected final List<IRule> rules = new ReferenceArrayList<>();
}
