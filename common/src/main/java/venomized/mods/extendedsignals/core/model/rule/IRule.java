package venomized.mods.extendedsignals.core.model.rule;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.client.ui.rule.IRuleInterfaceBuilder;

public interface IRule {
    ResourceLocation id();

    boolean evaluate(IRuleEvaluationContext context);

    default void writeToNbt(CompoundTag tag) {
    }

    default void readFromNbt(CompoundTag tag) {
    }

    default CompoundTag serialize() {
        return Rules.serialize(this);
    }
}
