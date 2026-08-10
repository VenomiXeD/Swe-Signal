package venomized.mods.extendedsignals.core.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class VariantData {

    @Setter
    @Getter
    private int selectedVariant = 0;
    @Getter
    private Set<String> checkboxOptionsTicked = new HashSet<>();

    public void toggleCheckboxOption(String option) {
        if (checkboxOptionsTicked.contains(option)) {
            checkboxOptionsTicked.remove(option);
        } else {
            checkboxOptionsTicked.add(option);
        }
    }

    @Getter
    private final List<VariantOption> variants = new ArrayList<>();

    @Getter
    private final List<VariantOption> checkboxOptions = new ArrayList<>();

    public void addVariantOption(VariantOption option) {
        variants.add(option);
    }

    public void addCheckboxOption(VariantOption option) {
        checkboxOptions.add(option);
    }


    @OnlyIn(Dist.CLIENT)
    @Nullable
    public PartialModel getVariantModel() {
        if (selectedVariant < 0 || selectedVariant >= variants.size()) {
            return null;
        }

        return variants.get(selectedVariant).model().get();
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public List<PartialModel> getAdditionalFeatures() {
        return checkboxOptions.stream().filter(e -> checkboxOptionsTicked.contains(e.key())).map(e -> e.model().get()).toList();
    }

    public void read(CompoundTag tag) {
        selectedVariant = tag.getInt("selected_variant");
        ListTag selectedCheckboxes = tag.getList("checkbox_options", Tag.TAG_STRING);
        checkboxOptionsTicked.clear();
        for (int i = 0; i < selectedCheckboxes.size(); i++) {
            checkboxOptionsTicked.add(selectedCheckboxes.getString(i));
        }
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("selected_variant", selectedVariant);
        ListTag selectedCheckboxes = new ListTag();
        for (String checkedKey : checkboxOptionsTicked) {
            selectedCheckboxes.add(StringTag.valueOf(checkedKey));
        }
        tag.put("checkbox_options", selectedCheckboxes);

        return tag;
    }
}
