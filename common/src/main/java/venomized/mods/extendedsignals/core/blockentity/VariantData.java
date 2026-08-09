package venomized.mods.extendedsignals.core.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class VariantData {

    @Setter
    @Getter
    private int selectedVariant = 0;
    @Getter
    private final List<VariantOption> variants = new ArrayList<>();

    @Getter
    private final List<Object> additionalOptions = new ArrayList<>();

    public void addVariantOption(VariantOption option) {
        variants.add(option);
    }


    @OnlyIn(Dist.CLIENT)
    @Nullable
    public PartialModel getModel() {
        if (selectedVariant < 0 || selectedVariant >= variants.size()) {
            return null;
        }

        return variants.get(selectedVariant).model().get();
    }

    public void read(CompoundTag tag) {
        selectedVariant = tag.getInt("selected_variant");
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("selected_variant", selectedVariant);

        return tag;
    }
}
