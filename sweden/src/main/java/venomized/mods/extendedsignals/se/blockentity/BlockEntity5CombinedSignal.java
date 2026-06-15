package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public class BlockEntity5CombinedSignal extends BlockEntitySignal<ICombinedSignalAspect> {
    public BlockEntity5CombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(0, 47.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 40.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0),
                new SignalLightPlacement(0, 19.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0)
        };
    }

    /**
     * @param state
     * @param direction
     * @return
     */
    @Override
    public @NotNull ICombinedSignalAspect interpret(final SignalStateNode state, Direction.AxisDirection direction) {
        return (ticks, lights) -> {
            final boolean blink = ticks % 20 > 10;
            SignalStateNode distant = state.getNextState();

            if (state.isStop()) {
                ISignalAspect.RGB.BLACK.apply(lights[0]);
                ISignalAspect.RGB.RED.apply(lights[1]);
                ISignalAspect.RGB.BLACK.apply(lights[2]);
                ISignalAspect.RGB.BLACK.apply(lights[3]);
                ISignalAspect.RGB.BLACK.apply(lights[4]);

                return;
            }

            if (distant == null) {
                ISignalAspect.RGB.GREEN.apply(lights[0]);
                if (state.getMaxProceedSpeed() >= 80) {
                    // PROCEED 80
                    ISignalAspect.RGB.BLACK.apply(lights[1]);
                    ISignalAspect.RGB.BLACK.apply(lights[2]);
                    ISignalAspect.RGB.BLACK.apply(lights[3]);
                    ISignalAspect.RGB.BLACK.apply(lights[4]);
                } else {
                    // PROCEED 40
                    ISignalAspect.RGB.BLACK.apply(lights[1]);
                    ISignalAspect.RGB.GREEN.apply(lights[2]);
                    ISignalAspect.RGB.BLACK.apply(lights[3]);
                    ISignalAspect.RGB.BLACK.apply(lights[4]);
                }

                return;
            }

            if (distant.isStop()) {
                if (state.getMaxProceedSpeed() < 80) {
                    if (state.getDistanceToNextSignal() <= 450) {
                        // PROCEED 40, SHORT ROUTE
                        ISignalAspect.RGB.GREEN.apply(lights[0]);
                        ISignalAspect.RGB.BLACK.apply(lights[1]);
                        ISignalAspect.RGB.GREEN.apply(lights[2]);
                        ISignalAspect.RGB.BLACK.apply(lights[3]);
                        ISignalAspect.RGB.GREEN.apply(lights[4]);

                        return;
                    } else {
                        //
                        ISignalAspect.RGB.GREEN.apply(lights[0]);
                        ISignalAspect.RGB.BLACK.apply(lights[1]);
                        ISignalAspect.RGB.GREEN.apply(lights[2]);
                        ISignalAspect.RGB.BLACK.apply(lights[3]);
                        ISignalAspect.RGB.BLACK.apply(lights[4]);

                        return;
                    }

                } else {
                    // PROCEED 80, EXPECT STOP
                    ISignalAspect.RGB.GREEN.apply(lights[0]);
                    ISignalAspect.RGB.BLACK.apply(lights[1]);
                    (blink ? ISignalAspect.RGB.BLACK : ISignalAspect.RGB.GREEN).apply(lights[2]);
                    ISignalAspect.RGB.BLACK.apply(lights[3]);
                    ISignalAspect.RGB.BLACK.apply(lights[4]);

                    return;
                }
            }

            if (state.getMaxProceedSpeed() >= 80 && distant.getMaxProceedSpeed() >= 80) {
                // PROCEED 80, EXPECT PROCEED 80 e
                ISignalAspect.RGB.GREEN.apply(lights[0]);
                ISignalAspect.RGB.BLACK.apply(lights[1]);
                ISignalAspect.RGB.BLACK.apply(lights[2]);
                (blink ? ISignalAspect.RGB.BLACK : ISignalAspect.RGB.WHITE).apply(lights[3]);
                ISignalAspect.RGB.BLACK.apply(lights[4]);

                return;
            }

            ISignalAspect.RGB.GREEN.apply(lights[0]);
            ISignalAspect.RGB.BLACK.apply(lights[1]);
            ISignalAspect.RGB.GREEN.apply(lights[2]);
            ISignalAspect.RGB.BLACK.apply(lights[3]);
            ISignalAspect.RGB.BLACK.apply(lights[4]);
            //throw new UnsupportedOperationException("An invalid state has been provided and this part should not be reached, report to developers\nState: " + NbtUtils.prettyPrint(state.toNBT()));
        };
    }

}
