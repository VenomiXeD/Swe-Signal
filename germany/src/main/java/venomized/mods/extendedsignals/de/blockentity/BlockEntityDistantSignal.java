package venomized.mods.extendedsignals.de.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.client.blockentityrenderer.SignalLightPlacement;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public class BlockEntityDistantSignal extends venomized.mods.extendedsignals.core.blockentity.BlockEntityDistantSignal {
    public BlockEntityDistantSignal(BlockEntityType<?> t, BlockPos pPos, BlockState pBlockState) {
        super(t, pPos, pBlockState);
    }

    public enum DistantSignalAspect implements IDistantSignalAspect {
        EXPECT_STOP(
                false,
                false,
                false
        ),
        EXPECT_PROCEED(
                false,
                true,
                true
        ),
        EXPECT_PROCEED_REDUCED_SPEED(
                false,
                true,
                false
        );


        DistantSignalAspect(
                boolean shortBrakingDistance,
                boolean upperGreen,
                boolean bottomGreen
        ) {
            this.shortBrakingDistance = shortBrakingDistance;
            this.upperGreen = upperGreen;
            this.bottomGreen = bottomGreen;
        }

        final boolean shortBrakingDistance;
        final boolean upperGreen;
        final boolean bottomGreen;

        /**
         * @param totalTicksForBlockEntity
         * @param states
         */
        @Override
        public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
            (shortBrakingDistance ? RGB.WHITE : RGB.BLACK).apply(states[0]);

            (upperGreen ? RGB.BLACK : RGB.YELLOW).apply(states[1]);
            (upperGreen ? RGB.GREEN : RGB.BLACK).apply(states[2]);

            (bottomGreen ? RGB.BLACK : RGB.YELLOW).apply(states[3]);
            (bottomGreen ? RGB.GREEN : RGB.BLACK).apply(states[4]);
        }
    }

    /**
     * @param rawState
     * @return
     */
    @Override
    public IDistantSignalAspect interpret(RawSignalState rawState) {
        RawSignalState distant = rawState.getNextState();
        if (rawState == null || !rawState.isReserved())
            return DistantSignalAspect.EXPECT_STOP;

        return distant.isProceed()
                ? distant.getMaxProceedSpeed() <= 40
                  ? DistantSignalAspect.EXPECT_PROCEED_REDUCED_SPEED : DistantSignalAspect.EXPECT_PROCEED
                : DistantSignalAspect.EXPECT_STOP;
    }

    /**
     * @return
     */
    @Override
    public SignalLightPlacement[] constructLightPlacements() {
        return new SignalLightPlacement[]{
                new SignalLightPlacement(5.25d / 16d, 99.5d / 16d, -6.5d / 16d, 1.5f, 1.5f, 0.5f),

                new SignalLightPlacement(-2.25 / 16d, 99d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
                new SignalLightPlacement(-6.25 / 16d, 99d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),

                new SignalLightPlacement(6 / 16d, 89.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
                new SignalLightPlacement(2 / 16d, 89.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f),
        };
    }
}
