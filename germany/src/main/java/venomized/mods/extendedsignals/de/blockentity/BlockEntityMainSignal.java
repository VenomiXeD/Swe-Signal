package venomized.mods.extendedsignals.de.blockentity;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public class BlockEntityMainSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntityMainSignal {
    public BlockEntityMainSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    @RequiredArgsConstructor
    public enum MainSignalAspect implements IMainSignalAspect {
        PROCEED(
                RGB.GREEN,
                RGB.BLACK,
                RGB.BLACK,
                RGB.BLACK,
                RGB.BLACK,
                RGB.BLACK
        ),
        PROCEED_40(
                RGB.GREEN,
                RGB.BLACK,
                RGB.BLACK,
                RGB.BLACK,
                RGB.BLACK,
                RGB.YELLOW
        ),
        STOP(
                RGB.BLACK,
                RGB.RED,
                RGB.RED,
                RGB.BLACK,
                RGB.BLACK,
                RGB.BLACK
        );

        MainSignalAspect(
                RGB top,
                RGB topleft,
                RGB topright,
                RGB rightshunt,
                RGB leftshunt,
                RGB bottom
        ) {
            colors = new RGB[]{top, topleft, topright, rightshunt, leftshunt, bottom};
        }

        private RGB[] colors;

        /**
         * @param totalTicksForBlockEntity
         * @param states
         */
        @Override
        public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
            for (int i = 0; i < states.length; i++) {
                colors[i].apply(states[i]);
            }
        }
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public IMainSignalAspect interpret(RawSignalState rawState) {
        return rawState.isProceed() ? MainSignalAspect.PROCEED : MainSignalAspect.STOP;
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(2.75d / 16d, 114 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),
                new SignalLightPlacement(2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),
                new SignalLightPlacement(-2.75d / 16d, 109 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f),

                new SignalLightPlacement(-2.75d / 16d, 105.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f),
                new SignalLightPlacement(2.75d / 16d, 100.25 / 16d, -7.65 / 16d, 1.75f, 1.75f, 0.1f),

                new SignalLightPlacement(2.75d / 16d, 96.25 / 16d, -7.65 / 16d, 2.75f, 2.75f, 0.1f)
        };
    }
}
