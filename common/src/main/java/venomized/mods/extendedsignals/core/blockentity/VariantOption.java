package venomized.mods.extendedsignals.core.blockentity;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public record VariantOption(String key, Component variantOptionDisplayName, Supplier<PartialModel> model) {
}
