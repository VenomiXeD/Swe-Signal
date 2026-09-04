package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.client.GermanyModels;
import venomized.mods.extendedsignals.de.signalling.KsCombinedSignalAspect;

public class BlockEntityKsCombinedSignal extends BlockEntityKs<ICombinedSignalAspect> {
    public BlockEntityKsCombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    protected boolean withDistantScreen() {
        return true;
    }

    /**
     * @return
     */
    @Override
    protected boolean withScreen() {
        return true;
    }

    /**
     *
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("braking_distance", SignalLight.whiteLight(3.5d / 16d, 96.5 / 16f, -8.4 / 16d, 1.75f, 1.75f, 0f))

                .withLight("stop", SignalLight.redLight(0d, 93.25 / 16d, -8.55 / 16d, 2.75f, 2.75f, 0f))
                .withLight("proceed", SignalLight.greenLight(2.5d / 16d, 89.25d / 16d, -8.55d / 16d, 2.75f, 2.75f, 0f))
                .withLight("danger", SignalLight.yellowLight(-2.5d / 16d, 89.25d / 16f, -8.55 / 16d, 2.75f, 2.75f, 0f))

                .withLight("zs7_0", SignalLight.yellowLight(2.5d / 16d, 85.5d / 16d, -8.4d / 16d, 1.75f, 1.75f, 0f))
                .withLight("zs7_1", SignalLight.yellowLight(-3.5d / 16d, 85.5d / 16d, -8.4d / 16d, 1.75f, 1.75f, 0f))
                .withLight("zs7_2", SignalLight.yellowLight(-0.5d / 16d, 82.5d / 16d, -8.4d / 16d, 1.75f, 1.75f, 0f))

                .withLight("unused", SignalLight.yellowLight(-0.5d / 16d, 85.5d / 16d, -8.4d / 16d, 1.75f, 1.75f, 0f))

                .withLight("zs1", SignalLight.whiteLight(3.5 / 16d, 82.5 / 16d, -8.4d / 16d, 1.75f, 1.75f, 0f))

                .withFadeSeconds(0.1f);
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (this.variantData().getCheckboxOptionsTicked().contains("zs3v_metal")) {
            state.getMiscTags().put("zs3v_metal", "present");
        }
        return KsCombinedSignalAspect.interpret(state, incomingDirection);
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variants = super.constructVariantData();
        variants.addVariantOptionLeft(new VariantData.VariantOption("400_left", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.400.left"), () -> GermanyModels.KSModels.KS_HP_VR_400_LEFT));
        variants.addVariantOptionRight(new VariantData.VariantOption("400_right", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.400.right"), () -> GermanyModels.KSModels.KS_HP_VR_400_RIGHT));
        variants.addVariantOptionLeft(new VariantData.VariantOption("1000_left", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.1000.left"), () -> GermanyModels.KSModels.KS_HP_VR_1000_LEFT));
        variants.addVariantOptionRight(new VariantData.VariantOption("1000_right", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.1000.right"), () -> GermanyModels.KSModels.KS_HP_VR_1000_RIGHT));
        return variants;
    }
}
