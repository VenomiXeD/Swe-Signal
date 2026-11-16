package venomized.mods.extendedsignals.core.model.rule;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Objects;

public class Rules {
    private static final Map<ResourceLocation, RuleType<?>> RULES = new Object2ReferenceArrayMap<>();

    public static <T extends Rule<T>> RuleType<T> registerRule(RuleType<T> rule) {
        if (RULES.putIfAbsent(rule.id(), rule) != null)
            throw new IllegalStateException("Duplicate rule ID: " + rule.id());

        return rule;
    }

    public static <T extends Rule<T>> RuleType<T> registerRule(ResourceLocation id, IRuleFactory<T> factory) {
        return registerRule(new RuleType<>(id, factory));
    }

    static <T extends Rule<T>> RuleType<T> registerRule(String name, IRuleFactory<T> factory) {
        return registerRule(ResourceLocation.fromNamespaceAndPath("extended_signals", name), factory);
    }

    public static RuleType<?> getRule(ResourceLocation id) {
        return Objects.requireNonNull(RULES.get(id));
    }

    public static RuleType<EmptyRule> RULE_EMPTY = registerRule("empty", EmptyRule::new);
    public static RuleType<ConstantRule> RULE_CONSTANT = registerRule("constant", ConstantRule::new);
    public static RuleType<NotRule> RULE_NOT = registerRule("invert", NotRule::new);
    public static RuleType<AndRule> RULE_AND = registerRule("and", AndRule::new);
    public static RuleType<OrRule> RULE_OR = registerRule("or", OrRule::new);

    public static RuleType<PathLabelRule> RULE_PATH_LABEL = registerRule("path", PathLabelRule::new);
    public static RuleType<CartCountRule> RULE_CART_COUNT = registerRule("cart_count", CartCountRule::new);


    public static CompoundTag serialize(IRule instance) {
        CompoundTag serialized = new CompoundTag();
        NBTHelper.writeResourceLocation(serialized, "type", instance.id());

        CompoundTag ruleData = new CompoundTag();
        instance.writeToNbt(ruleData);
        serialized.put("data", ruleData);

        return serialized;
    }

    public static IRule deserialize(CompoundTag nbt) {
        ResourceLocation id = NBTHelper.readResourceLocation(nbt, "type");
        CompoundTag data = nbt.getCompound("data");

        IRule instance = getRule(id).create();
        instance.readFromNbt(data);
        return instance;
    }
}
