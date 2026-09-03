package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.blockentity.VariantData;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.se.client.SwedenModels;

public class BlockEntity5CombinedSignal extends BlockEntitySwedishSignal<ICombinedSignalAspect> {
    public BlockEntity5CombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }


    /**
     *
     */
    @Override
    public void configureSignalLights(SignalContainer signalLights) {
        signalLights
                .withLight("l0", new SignalLight(0, 47.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l1", new SignalLight(0, 40.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 0, 0))
                .withLight("l2", new SignalLight(0, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l3", new SignalLight(0, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 255, 255))
                .withLight("l4", new SignalLight(0, 19.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0));
    }

    /**
     * @return
     */
    @Override
    protected VariantData constructVariantData() {
        VariantData variants = super.constructVariantData();
        variants.addVariantOptionRight(new VariantData.VariantOption("gantry", Component.translatable("screens.extended_signals_se.modelconfig.main_signal.gantry"), () -> SwedenModels.SIGNAL_5L_GANTRY));
        return variants;
    }

    /**
     * @param state
     * @param direction
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(final SignalStateNode state, Direction.AxisDirection direction) {
        return (seconds, lights) -> {
            boolean is40 = false;
            final boolean blink = ISignalAspect.blink(seconds, 80, 0.5f);

            SignalStateNode distant = state.getNextState();

            if (state.isStop()) {
                lights.powered("l1");
                return;
            }

            if (state.getMaxProceedSpeed() >= 80) {
                lights.powered("l0");
            } else {
                is40 = true;
                lights.powered("l0");
                lights.powered("l2");
            }

            if (distant == null || distant.isStop()) {
                if (blink)
                    lights.powered("l2");
                if (is40 && state.getDistanceToNextSignal() <= 450)
                    lights.powered("l4");

                return;
            }

            if (distant.getMaxProceedSpeed() >= 80) {
                if (blink)
                    lights.powered("l3");
            } else {
                if (blink && !is40) {
                    lights.powered("l2");
                    lights.powered("l4");
                }
            }
            //throw new UnsupportedOperationException("An invalid state has been provided and this part should not be reached, report to developers\nState: " + NbtUtils.prettyPrint(state.toNBT()));
        };
    }

}
