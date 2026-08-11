package venomized.mods.extendedsignals.se.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLight;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public class BlockEntity5CombinedSignal extends BlockEntitySignal<ICombinedSignalAspect> {
    public BlockEntity5CombinedSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }


    /**
     * @return
     */
    @Override
    public SignalLighting constructSignalLighting() {
        return new SignalLighting()
                .withLight("l0", new SignalLight(0, 47.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l1", new SignalLight(0, 40.75d / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 0, 0))
                .withLight("l2", new SignalLight(0, 33.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0))
                .withLight("l3", new SignalLight(0, 26.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(255, 255, 255))
                .withLight("l4", new SignalLight(0, 19.75 / 16d, 0.25d / 16d, 3.25f, 3.25f, 0).withDefaultColor(0, 255, 0));
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
                lights.powered("l1");
                return;
            }

            if (distant == null) {
                lights.powered("l0");
                if (state.getMaxProceedSpeed() < 80) {
                    lights.powered("l2");
                }
                return;
            }

            if (distant.isStop()) {
                if (state.getMaxProceedSpeed() < 80) {
                    if (state.getDistanceToNextSignal() <= 450) {
                        // PROCEED 40, SHORT ROUTE
                        lights.powered("l0");
                        lights.powered("l2");
                        lights.powered("l4");
                        return;
                    } else {
                        //
                        lights.powered("l0");
                        lights.powered("l2");
                        return;
                    }

                } else {
                    // PROCEED 80, EXPECT STOP
                    lights.powered("l0");
                    if (blink)
                        lights.powered("l2");

                    return;
                }
            }

            if (state.getMaxProceedSpeed() >= 80 && distant.getMaxProceedSpeed() >= 80) {
                // PROCEED 80, EXPECT PROCEED 80
                lights.powered("l0");
                if (blink)
                    lights.powered("l3");
                return;
            }

            lights.powered("l0");
            lights.powered("l2");
            //throw new UnsupportedOperationException("An invalid state has been provided and this part should not be reached, report to developers\nState: " + NbtUtils.prettyPrint(state.toNBT()));
        };
    }

}
