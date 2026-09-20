package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Vector3f;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.de.block.GermanyBlocks;
import venomized.mods.extendedsignals.de.blockentity.hvk.BlockEntityHVKBlockCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.hvk.BlockEntityHVKCombinedSignal;
import venomized.mods.extendedsignals.de.blockentity.hvk.BlockEntityHVKSignal;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class RendererHVk<T extends BlockEntityHVKSignal<?>> extends RendererZs3Zs3vCapableSignal<T> {
    public RendererHVk(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f metalZs3Corner0() {
        return new Vector3f(2.25f / 16f, 109.5f / 16f + getYOffset(), -7.6f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f metalZs3Corner1() {
        return new Vector3f(-2.25f / 16f, 103.25f / 16f + getYOffset(), -7.6f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f metalZs3vCorner1() {
        return new Vector3f(2.25f / 16f, 74f / 16f, -14.1f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f metalZs3vCorner0() {
        return new Vector3f(-2.25f / 16f, 67.75f / 16f, -14.1f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f matrixZs3Corner0() {
        return new Vector3f(3f / 16f, 110f / 16f + getYOffset(), -8.4f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f matrixZs3Corner1() {
        return new Vector3f(-3f / 16f, 103f / 16f + getYOffset(), -8.4f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f matrixZs3vCorner0() {
        return new Vector3f(3f / 16f, 75f / 16f, -16.9f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f matrixZs3vCorner1() {
        return new Vector3f(-3f / 16f, 68f / 16f, -16.9f / 16f);
    }

    /**
     *
     */
    @Override
    public void renderAdditionalModels() {
        PartialModel signalTypeIdentifierPlate = blockEntity.getSign();

        float offset = switch (blockEntity.variantData().getSelectedVariant()) {
            case 0 -> -17.5f; // 1.0 left
            case 1 -> -8.5f; // 0.4 left
            case 3 -> 8.5f; // 0.4 right
            case 4 -> 17.5f; // 1.0 right
            default -> 0f;
        };
        if (signalTypeIdentifierPlate != null) {
            quickRenderPartialModel(signalTypeIdentifierPlate, offset / 16f, 0, 0);
        }

        // A little bit of special implementation, because the position of the matrix display is not fixed unlike Ks signals
        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3_matrix")) {
            quickRenderPartialModel(GermanyModels.HVKModels.ZS3_MATRIX, 0, getYOffset(), 0);
        }

        if (blockEntity.variantData().getCheckboxOptionsTicked().contains("zs3_metal")) {
            quickRenderPartialModel(GermanyModels.HVKModels.ZS3_METAL, 0, getYOffset(), 0);
        }

        super.renderAdditionalModels();
    }

    /**
     * @param aspect
     * @param signalLights
     */
    @Override
    public void renderAdditionalSignals(ISignalAspect aspect, SignalContainer signalLights) {
        super.renderAdditionalSignals(aspect, signalLights);
        if (blockEntity.getBlockState().is(GermanyBlocks.HVKBlocks.HVK_REPEATER_SIGNAL))
            signalLights.powered("vr_braking_distance");
    }

    private float getYOffset() {
        return blockEntity instanceof BlockEntityHVKCombinedSignal || blockEntity instanceof BlockEntityHVKBlockCombinedSignal ? 19f / 16f : 0;
    }
}
