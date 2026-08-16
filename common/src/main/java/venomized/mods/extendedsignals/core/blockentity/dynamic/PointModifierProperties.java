package venomized.mods.extendedsignals.core.blockentity.dynamic;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PointModifierProperties {
    @Getter
    private final Map<String, Property<?>> properties = new Object2ReferenceLinkedOpenHashMap<>();

    public PointModifierProperties() {
    }

    public void addProperty(String propertyKeyName, Property<?> property) {
        properties.put(propertyKeyName, property);
    }

    public Property<?> getProperty(String propertyKeyName) {
        return properties.get(propertyKeyName);
    }

    public void fromNBT(CompoundTag tag) {
        CompoundTag props = tag.getCompound("properties");
        for (String propertyKeyName : props.getAllKeys()) {
            properties.get(propertyKeyName).deserializeValue(props.getCompound(propertyKeyName));
        }
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag values = new CompoundTag();
        properties.forEach((s, property) -> {
            values.put(s, property.serializeValue());
        });
        tag.put("properties", values);
        return tag;
    }

    public void copyTo(Map<String, Object> otherMap) {
        otherMap.clear();
        otherMap.putAll(properties);
    }
}
