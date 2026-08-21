package venomized.mods.extendedsignals.se.signaling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum DistantSignalAspect implements IDistantSignalAspect {
    EXPECT_80(
            false,
            true,
            false
    ),
    EXPECT_40(
            true,
            false,
            true
    ),
    EXPECT_40_SHORT(
            false,
            false,
            false
    ),
    EXPECT_STOP(
            true,
            false,
            false
    ),
    NONE(
            false,
            false,
            false
    );

    private final boolean l0;
    private final boolean l1;
    private final boolean l2;

    public static DistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        SignalStateNode distant = state.getNextState();
        if (distant == null || !distant.isProceed()) {
            return DistantSignalAspect.EXPECT_STOP;
        }

        return distant.getMaxProceedSpeed() >= 80 ? EXPECT_80 :
                state.getDistanceToNextSignal() > 450 ? EXPECT_40 : EXPECT_40_SHORT;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalLighting states) {
        boolean lit = seconds % 1 > .5f;


        if (l0 && lit)
            states.powered("l0");
        if (l1 && lit)
            states.powered("l1");
        if (l2 && lit)
            states.powered("l2");
    }
}
