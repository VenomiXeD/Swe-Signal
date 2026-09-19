package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.util.SpriteUV;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

@OnlyIn(Dist.CLIENT)
public class GermanRenderHelper {
    public static final ResourceLocation ZS3_NUMBERS = ExtendedSignalsGermany.res("textures/block/signals/de/ks_zs3_numbers.png");
    public static final ResourceLocation ZS3V_NUMBERS = ExtendedSignalsGermany.res("textures/block/signals/de/ks_zs3v_numbers.png");

    public static final ResourceLocation MATRIX_CHARACTERS = ExtendedSignalsGermany.res("textures/block/signals/de/ks_characters.png");

    public static @Nullable SpriteUV getZs3MetalPlateUV(int kph) {
        if (kph <= 0 || kph > 16)
            return null;

        final int TEX_W = 176;
        final int TEX_H = 20;

        final int CELL_W = 10;
        final int CELL_H = kph >= 10 ? 18 : 16;
        final int OFFSET_H = kph >= 10 ? 1 : 3;

        final int u0 = (kph - 1) * CELL_W + (kph - 1);
        final int u1 = u0 + CELL_W;
        final int v0 = OFFSET_H;
        final int v1 = v0 + CELL_H;

        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H, ZS3_NUMBERS);
    }

    public static @Nullable SpriteUV getZs3vMetalPlateUV(int kph) {
        if (kph <= 0 || kph > 16)
            return null;

        final int TEX_W = 176;
        final int TEX_H = 19;

        final int CELL_W = 10;
        final int CELL_H = kph >= 10 ? 18 : 16;

        final int u0 = (kph - 1) * CELL_W + (kph - 1);
        final int u1 = u0 + CELL_W;
        final int v0 = 0;
        final int v1 = v0 + CELL_H;

        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H, ZS3V_NUMBERS);
    }

    public static @Nullable SpriteUV getZs3MatrixSpeedUV(final int kph, final boolean orange) {
        if (kph <= 0 || kph > 16)
            return null;

        final int TEX_W = 298;
        final int TEX_H = 74;

        final int CELL_W = 12;
        final int CELL_H = 14;
        final int u0 = (kph - 1) * CELL_W + (kph - 1);
        final int u1 = u0 + CELL_W;
        final int v0 = orange ? 15 : 0;
        final int v1 = v0 + CELL_H;

        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H, MATRIX_CHARACTERS);
    }
}
