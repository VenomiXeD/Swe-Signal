package venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.math.NumberUtils;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.se.ExtendedSignalsSweden;
import venomized.mods.extendedsignals.se.blockentity.sign.BlockEntitySpeedSign;

public class RendererSpeedSign extends RendererGeneric<BlockEntitySpeedSign> {
    public RendererSpeedSign(BlockEntityRendererProvider.Context context) {
    }

    /**
     *
     */
    @Override
    public void doRender() {
        super.doRender();
        int num = NumberUtils.toInt(blockEntity.variantData().getTextBoxValues().get("number"));
        if (num >= 10 && num <= 120) {
            int speed = Mth.floor((float) num / 10);
            renderUVMappedTexturedDisplay(
                    blockEntity.corner0(), blockEntity.corner1(), calculateSpriteUV(
                            speed - 1, 192, 16, 16, 0, ExtendedSignalsSweden.res("textures/block/signs/se/speed_numbers.png")
                    ), false
            );
        }
    }
}
