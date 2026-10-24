package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.apache.commons.lang3.math.NumberUtils;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySingleTextModel;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

public class RendererLf<T extends BlockEntitySingleTextModel> extends RendererGeneric<T> {
    public RendererLf(BlockEntityRendererProvider.Context context) {
    }

    /**
     *
     */
    @Override
    public void doRender() {
        super.doRender();
        int num = NumberUtils.toInt(blockEntity.variantData().getTextBoxValues().get("number"));
        if (num >= 1 && num <= 16) {
            renderUVMappedTexturedDisplay(
                    blockEntity.corner0(), blockEntity.corner1(), calculateSpriteUV(
                            num - 1, 175, 16, 10, 1, ExtendedSignalsGermany.res("textures/block/signs/de/lf_numbers.png")
                    ), false
            );
        }
    }
}
