package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.util.SpriteUV;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityKs;

public class RendererKs<T extends BlockEntityKs<?>> extends RendererSignal<T> {
    private static final ResourceLocation KS_ZS3_NUMBERS = ExtendedSignalsGermany.res("textures/block/signals/de/ks_zs3_numbers.png");
    private static final ResourceLocation KS_ZS3V_NUMBERS = ExtendedSignalsGermany.res("textures/block/signals/de/ks_zs3v_numbers.png");

    private static final ResourceLocation KS_MATRIX_CHARACTERS = ExtendedSignalsGermany.res("textures/block/signals/de/ks_characters.png");

    public RendererKs(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    private static @Nullable SpriteUV getZs3MetalPlateUV(int kph) {
        if (kph <= 0 || kph > 16)
            return null;

        final int TEX_W = 176;
        final int TEX_H = 20;

        final int CELL_W = 10;
        final int CELL_H = kph >= 10 ? 18 : 16;
        final int OFFSET_H = kph >= 10 ? 1 : 3;

        final int v0 = OFFSET_H;
        final int v1 = v0 + CELL_H;
        final int u0 = (kph - 1) * CELL_W + (kph - 1);
        final int u1 = u0 + CELL_W;

        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H);
    }

    private static @Nullable SpriteUV getZs3vMetalPlateUV(int kph) {
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

        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H);
    }

    private static SpriteUV getZs3MatrixSpeedUV(int kph, boolean zs3v) {
        if (kph <= 0 || kph > 16)
            return null;

        final int TEX_W = 298;
        final int TEX_H = 74;

        final int CELL_W = 12;
        final int CELL_H = 14;
        final int u0 = (kph - 1) * CELL_W + (kph - 1);
        final int u1 = u0 + CELL_W;
        final int v0 = 0;
        final int v1 = v0 + CELL_H;

        return new SpriteUV((float) u0 / TEX_W, (float) v0 / TEX_H, (float) u1 / TEX_W, (float) v1 / TEX_H);
    }

    /**
     * @param aspect
     */
    @Override
    public void renderAdditionalSignals(ISignalAspect aspect) {
        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3_metal")) {
            String in = blockEntity.variantData().getTextBoxValues().getOrDefault("zs3_value", "");
            int speedInput = NumberUtils.toInt(in);
            SpriteUV uv = getZs3MetalPlateUV(speedInput);
            if (uv != null) {
                renderUVMappedTexturedDisplay(
                        new Vector3f(2.25f / 16f, 109.5f / 16f, -7.6f / 16f),
                        new Vector3f(-2.25f / 16f, 103.25f / 16f, -7.6f / 16f),
                        KS_ZS3_NUMBERS,
                        uv,
                        false
                );
            }
        }

        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3v_metal")) {
            String in = blockEntity.variantData().getTextBoxValues().getOrDefault("zs3v_value", "");
            int speedInput = NumberUtils.toInt(in);
            SpriteUV uv = getZs3vMetalPlateUV(speedInput);
            if (uv != null) {
                renderUVMappedTexturedDisplay(
                        new Vector3f(2.25f / 16f, 74f / 16f, -14.1f / 16f),
                        new Vector3f(-2.25f / 16f, 67.75f / 16f, -14.1f / 16f),
                        KS_ZS3V_NUMBERS,
                        uv,
                        false
                );
            }
        }
    }
}
