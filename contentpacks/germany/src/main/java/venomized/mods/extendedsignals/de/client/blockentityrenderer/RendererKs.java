package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Vector3f;

public class RendererKs<T extends venomized.mods.extendedsignals.de.blockentity.ks.BlockEntityKs<?>> extends RendererZs3Zs3vCapableSignal<T> {
    public RendererKs(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f metalZs3Corner0() {
        return new Vector3f(2.25f / 16f, 109.5f / 16f, -7.6f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f metalZs3Corner1() {
        return new Vector3f(-2.25f / 16f, 103.25f / 16f, -7.6f / 16f);
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
        return new Vector3f(3f / 16f, 110f / 16f, -8.4f / 16f);
    }

    /**
     * @return
     */
    @Override
    protected Vector3f matrixZs3Corner1() {
        return new Vector3f(-3f / 16f, 103f / 16f, -8.4f / 16f);
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
}
