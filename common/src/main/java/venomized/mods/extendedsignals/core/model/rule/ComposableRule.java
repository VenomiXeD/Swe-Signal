package venomized.mods.extendedsignals.core.model.rule;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import lombok.Getter;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;

public abstract class ComposableRule<S extends Rule<S>> extends Rule<S> {
    @Getter
    private final List<IRule> children = new ReferenceArrayList<>();

    public void addRule(IRule rule) {
        children.add(rule);
    }

    public IRule ruleAt(int index) {
        return children.get(index);
    }

    public void removeRule(IRule rule) {
        children.remove(rule);
    }

    public void removeRule(int index) {
        children.remove(index);
    }

    public ComposableRule(RuleType<S> selfRuleType) {
        super(selfRuleType);
    }

    /**
     * @param tag
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        super.writeToNbt(tag);
        ListTag listTag = new ListTag();
        for (IRule rule : getChildren()) {
            listTag.add(Rules.serialize(rule));
        }
        tag.put("rules", listTag);
    }

    /**
     * @param tag
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        super.readFromNbt(tag);
        NBTHelper.iterateCompoundList(tag.getList("rules", Tag.TAG_COMPOUND), ruleNBT -> children.add(Rules.deserialize(ruleNBT)));
    }
}
