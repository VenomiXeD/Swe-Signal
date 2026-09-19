package venomized.mods.extendedsignals.core.util;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public record SpriteUV(float u0, float v0, float u1, float v1, @Nullable ResourceLocation texture) {
}
