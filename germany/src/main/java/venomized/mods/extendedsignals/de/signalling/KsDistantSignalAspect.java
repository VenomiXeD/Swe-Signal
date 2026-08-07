package venomized.mods.extendedsignals.de.signalling;

import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

public enum KsDistantSignalAspect implements ISignalAspect {
    EXPECT_PROCEED,
    EXPECT_REDUCED_SPEED,
    EXPECT_STOP;

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        RGB.BLACK.apply(states[0]);
        RGB.BLACK.apply(states[1]);
        RGB.BLACK.apply(states[2]);
        switch (this) {
            case EXPECT_PROCEED:
                RGB.GREEN.apply(states[1]);
                break;
            case EXPECT_REDUCED_SPEED:
                (totalTicksForBlockEntity % 20 > 10 ? RGB.BLACK : RGB.GREEN).apply(states[1]);
                break;
            case EXPECT_STOP:
                RGB.YELLOW.apply(states[2]);
        }
    }
}
