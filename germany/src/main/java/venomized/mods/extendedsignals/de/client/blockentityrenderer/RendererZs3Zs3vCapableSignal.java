package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.math.NumberUtils;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererSignal;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.util.SpriteUV;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityGermanySignal;

public abstract class RendererZs3Zs3vCapableSignal<T extends BlockEntityGermanySignal<?>> extends RendererSignal<T> {
    public RendererZs3Zs3vCapableSignal(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    protected abstract Vector3f metalZs3Corner0();

    protected abstract Vector3f metalZs3Corner1();

    protected abstract Vector3f metalZs3vCorner0();

    protected abstract Vector3f metalZs3vCorner1();

    protected abstract Vector3f matrixZs3Corner0();

    protected abstract Vector3f matrixZs3Corner1();

    protected abstract Vector3f matrixZs3vCorner0();

    protected abstract Vector3f matrixZs3vCorner1();

    /**
     *
     */
    @Override
    public void renderAdditionalModels() {
        super.renderAdditionalModels();
        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3_metal")) {
            String in = blockEntity.variantData().getTextBoxValues().getOrDefault("zs3_value", "");
            int speedInput = NumberUtils.toInt(in);
            SpriteUV uv = GermanRenderHelper.getZs3MetalPlateUV(speedInput);
            if (uv != null) {
                renderUVMappedTexturedDisplay(
                        metalZs3Corner0(),
                        metalZs3Corner1(),
                        uv,
                        false
                );
            }
        }

        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3v_metal")) {
            String in = blockEntity.variantData().getTextBoxValues().getOrDefault("zs3v_value", "");
            int speedInput = NumberUtils.toInt(in);
            SpriteUV uv = GermanRenderHelper.getZs3vMetalPlateUV(speedInput);
            if (uv != null) {
                renderUVMappedTexturedDisplay(
                        metalZs3vCorner1(), // idk why this is flipped
                        metalZs3vCorner0(),
                        uv,
                        false
                );
            }
        }
    }

    /**
     * @param aspect
     */
    @Override
    public void renderAdditionalSignals(ISignalAspect aspect) {
        super.renderAdditionalSignals(aspect);
        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3_matrix")) {
            SpriteUV uv = GermanRenderHelper.getZs3MatrixSpeedUV(Mth.floor(blockEntity.getZs3MatrixDisplaySpeed()), false);
            if (uv != null) {
                renderUVMappedTexturedDisplay(
                        matrixZs3Corner0(),
                        matrixZs3Corner1(),
                        uv,
                        true
                );
            }
        }

        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3v_matrix")) {
            SpriteUV uv = GermanRenderHelper.getZs3MatrixSpeedUV(Mth.floor(blockEntity.getZs3vMatrixDisplaySpeed()), true);
            if (uv != null) {
                renderUVMappedTexturedDisplay(
                        matrixZs3vCorner0(),
                        matrixZs3vCorner1(),
                        uv,
                        true
                );
            }
        }
    }
}
