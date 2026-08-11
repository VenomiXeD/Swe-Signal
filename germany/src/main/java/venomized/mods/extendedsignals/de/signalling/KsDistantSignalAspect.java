package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public enum KsDistantSignalAspect implements IDistantSignalAspect {
    EXPECT_PROCEED,
    EXPECT_REDUCED_SPEED,
    EXPECT_STOP;

    public static KsDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (state.getNextState() == null || !state.getNextState().isProceed()) {
            return KsDistantSignalAspect.EXPECT_STOP;
        }

        if (state.getNextState().getMaxProceedSpeed() >= state.getMaxProceedSpeed()) {
            return KsDistantSignalAspect.EXPECT_PROCEED;
        }

        return KsDistantSignalAspect.EXPECT_REDUCED_SPEED;
    }

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
                states.powered("danger");
        }
    }
}
