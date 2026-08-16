package venomized.mods.extendedsignals.de.client.blockentityrenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.de.blockentity.BlockEntityCrossingLight;

public class RendererCrossingLight extends RendererGeneric<BlockEntityCrossingLight> {
    private long startTick = -1;

    /**
     * @param context
     */
    public RendererCrossingLight(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void doRender() {
        renderSelfBlock();

        if (blockEntity.isActive()) {
            if (startTick == -1) {
                startTick = blockEntity.getLevel().getGameTime();
            }
        } else {
            startTick = -1;
        }

        long t = startTick == -1 ? -1 : blockEntity.getLevel().getGameTime() - startTick;

        boolean yellowLit = t >= 0 && t < 20 * 6;
        boolean redLit = t >= 20 * 6;

        renderLightAt(0f, 10.25d / 16d, -3.25d / 16d, 3.25f, 3.25f, 0f, redLit ? 255 : 0, 0, 0);
        renderLightAt(0f, 5.25d / 16d, -3.25d / 16d, 3.25f, 3.25f, 0f, yellowLit ? 255 : 0, yellowLit ? 255 : 0, 0);
    }
}
