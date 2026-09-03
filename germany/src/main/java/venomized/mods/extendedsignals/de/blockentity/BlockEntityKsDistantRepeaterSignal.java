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
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.de.client.GermanyModels;

public class BlockEntityKsDistantRepeaterSignal extends BlockEntityKs<IDistantSignalAspect> {
    public BlockEntityKsDistantRepeaterSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
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
     *
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("proceed", SignalLight.greenLight(2.5d / 16d, 88.25 / 16d, -8.55d / 16d, 2.75f, 2.75f, 0f))
                .withLight("danger", SignalLight.yellowLight(-2.5d / 16d, 88.25 / 16f, -8.55 / 16d, 2.75f, 2.75f, 0f))
                .withLight("repeater", SignalLight.whiteLight(3.5d / 16d, 82.25d / 16f, -8.4 / 16d, 1.75f, 1.75f, 0f))
                .withFadeSeconds(0.1f);
    }

    /**
     * @param state
     * @param incomingDirection
     * @return
     */
    @Override
    public @NotNull IDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        return (seconds, states) -> {
            if (state.getNextState() == null || !state.getNextState().isProceed()) {
                states.powered("danger");
                states.powered("repeater");
                return;
            }

            if (state.getNextState().getMaxProceedSpeed() >= state.getMaxProceedSpeed()) {
                states.powered("proceed");
                return;
            }

            if (seconds % 1f > 0.5f) {
                states.powered("proceed");
            }
        };
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variantData = super.constructVariantData();
        variantData.addVariantOptionLeft(new VariantData.VariantOption("400_left", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.400.left"), () -> GermanyModels.KSModels.KS_VR_R_400_LEFT));
        variantData.addVariantOptionRight(new VariantData.VariantOption("400_right", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.400.right"), () -> GermanyModels.KSModels.KS_VR_R_400_RIGHT));
        variantData.addVariantOptionLeft(new VariantData.VariantOption("1000_left", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.1000.left"), () -> GermanyModels.KSModels.KS_VR_R_1000_LEFT));
        variantData.addVariantOptionRight(new VariantData.VariantOption("1000_right", Component.translatable("screens.extended_signals_de.modelconfig.ks.offset.variant.1000.right"), () -> GermanyModels.KSModels.KS_VR_R_1000_RIGHT));
        return variantData;
    }
}
