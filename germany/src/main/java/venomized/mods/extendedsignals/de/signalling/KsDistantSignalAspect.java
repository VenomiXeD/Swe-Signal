package venomized.mods.extendedsignals.de.signalling;

import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
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
    public void applyAspect(long totalTicksForBlockEntity, SignalLighting states) {
        switch (this) {
            case EXPECT_PROCEED:
                states.powered("proceed");
                break;
            case EXPECT_REDUCED_SPEED:
                if ((totalTicksForBlockEntity % 20 > 10))
                    states.powered("proceed");
                break;
            case EXPECT_STOP:
                states.powered("stop");
        }
    }
}
