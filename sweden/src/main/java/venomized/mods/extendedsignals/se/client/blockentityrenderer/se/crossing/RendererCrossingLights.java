package venomized.mods.extendedsignals.se.client.blockentityrenderer.se.crossing;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.RendererGeneric;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityThreeLightCrossingLights;

public class RendererCrossingLights<T extends BlockEntityThreeLightCrossingLights> extends RendererGeneric<T> {
    public RendererCrossingLights(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     *
     */
    @Override
    public void doRender() {
        boolean blink = blockEntity.getLevel().getGameTime() % 40 < 20;
        super.doRender();
        renderSelfBlock();

        boolean standbyLit = !blockEntity.isActive() && blink;

        renderLightAt(
                0, 5.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                standbyLit ? 255 : 0,
                standbyLit ? 255 : 0,
                standbyLit ? 255 : 0
        );

        int redPower = blockEntity.isActive() ? 255 : 0;
        boolean alternate = blockEntity.getLevel().getGameTime() % 20 < 10;
        renderLightAt(
                4f / 16f, 12.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                alternate ? redPower : 0, 0, 0
        );
        renderLightAt(
                -4f / 16f, 12.2f / 16f, -1.2f / 16f,
                5f, 5f, 0f,
                alternate ? 0 : redPower, 0, 0
        );
        // boolean alternate = blockEntity.getLevel().getGameTime() % 20 < 10;
        // CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
        //         .translate(0.505f - 0.4f,5.25/16f,2.25f/16f)
        //         .scale(3f,3f,0f)
        //         .light(0xFFFFFF)
        //         .disableDiffuse()
        //         .color(alternate && !blockEntity.isInactive() ? 0xFFFF0000 : 0x00000000)
        //         .renderInto(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)));
        // CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
        //         .translate(0.505f + 0.4f,5.25/16f,2.25f/16f)
        //         .scale(3f,3f,0f)
        //         .light(0xFFFFFF)
        //         .disableDiffuse()
        //         .color(!alternate && !blockEntity.isInactive() ? 0xFFFF0000 : 0x00000000)
        //         .renderInto(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)));
//
        // CachedBuffers.partial(ExtendedSignalsCoreModels.LIGHT_MODEL, blockEntity.getBlockState())
        //         .translate(0.505f,5.25/16f,2.25f/16f)
        //         .scale(3f,3f,0f)
        //         .light(0xFFFFFF)
        //         .disableDiffuse()
        //         .color(blockEntity.isInactive() && blink ? 0xFFFFFFFF : 0x00000000)
        //         .renderInto(poseStack, bufferSource.getBuffer(RenderType.beaconBeam(SignalRendererHelper.SIGNAL_LIGHT_TEX_LOC, true)));
    }
}
