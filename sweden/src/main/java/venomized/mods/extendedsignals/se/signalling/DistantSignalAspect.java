package venomized.mods.extendedsignals.se.signalling;

import lombok.RequiredArgsConstructor;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum DistantSignalAspect implements IDistantSignalAspect {
    EXPECT_80(
            new LightEntry(RGB.BLACK, false),
            new LightEntry(RGB.WHITE, true),
            new LightEntry(RGB.BLACK, false)
    ),
    EXPECT_40(
            new LightEntry(RGB.GREEN, true),
            new LightEntry(RGB.BLACK, false),
            new LightEntry(RGB.GREEN, true)
    ),
    EXPECT_40_SHORT(
            new LightEntry(RGB.GREEN, false),
            new LightEntry(RGB.BLACK, false),
            new LightEntry(RGB.GREEN, false)
    ),
    EXPECT_STOP(
            new LightEntry(RGB.GREEN, true),
            new LightEntry(RGB.BLACK, false),
            new LightEntry(RGB.BLACK, false)
    ),
    NONE(
            new LightEntry(RGB.BLACK, false),
            new LightEntry(RGB.BLACK, false),
            new LightEntry(RGB.BLACK, false)
    );

    private final LightEntry l0;
    private final LightEntry l1;
    private final LightEntry l2;

    public static DistantSignalAspect interpret(SignalStateNode state) {
        SignalStateNode distant = state.getNextState();
        if (distant == null || distant.isStop()) {
            return DistantSignalAspect.EXPECT_STOP;
        }

        return distant.getMaxProceedSpeed() > 40 ? EXPECT_80 :
                state.getDistanceToNextSignal() > 450 ? EXPECT_40 : EXPECT_40_SHORT;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        l0.apply(totalTicksForBlockEntity, states[0]);
        l1.apply(totalTicksForBlockEntity, states[1]);
        if (states.length == 3 && states[2] != null)
            l2.apply(totalTicksForBlockEntity, states[2]);
    }
}
