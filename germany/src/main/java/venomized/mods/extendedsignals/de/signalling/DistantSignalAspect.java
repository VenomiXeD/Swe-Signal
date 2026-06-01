package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public enum DistantSignalAspect implements IDistantSignalAspect {
    OFF(
            false,
            false,
            false,
            true
    ),
    EXPECT_STOP(
            false,
            false,
            false,
            false
    ),
    EXPECT_PROCEED(
            false,
            true,
            true,
            false
    ),
    EXPECT_PROCEED_REDUCED_SPEED(
            false,
            true,
            false,
            false
    );

    final boolean off;
    final boolean shortBrakingDistance;
    final boolean upperGreen;
    final boolean bottomGreen;
    DistantSignalAspect(
            boolean shortBrakingDistance,
            boolean upperGreen,
            boolean bottomGreen,
            boolean off
    ) {
        this.shortBrakingDistance = shortBrakingDistance;
        this.upperGreen = upperGreen;
        this.bottomGreen = bottomGreen;
        this.off = off;
    }

    public static DistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        SignalStateNode distant = state.getNextState();
        if (distant == null || state.isStop())
            return DistantSignalAspect.OFF;

        return !distant.isStop()
                ? distant.getMaxProceedSpeed() <= 40
                  ? DistantSignalAspect.EXPECT_PROCEED_REDUCED_SPEED : DistantSignalAspect.EXPECT_PROCEED
                : DistantSignalAspect.EXPECT_STOP;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        if (off) {
            for (int i = 1; i < states.length; i++)
                RGB.BLACK.apply(states[i]);
            return;
        }

        (upperGreen ? RGB.BLACK : RGB.YELLOW).apply(states[1]);
        (upperGreen ? RGB.GREEN : RGB.BLACK).apply(states[2]);

        (bottomGreen ? RGB.BLACK : RGB.YELLOW).apply(states[3]);
        (bottomGreen ? RGB.GREEN : RGB.BLACK).apply(states[4]);
    }
}
