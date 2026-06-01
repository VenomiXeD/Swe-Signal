package venomized.mods.extendedsignals.se.signaling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum MainSignalAspect implements IMainSignalAspect {
    PROCEED_80(
            RGB.GREEN,
            RGB.BLACK,
            RGB.BLACK
    ),
    PROCEED_40(
            RGB.GREEN,
            RGB.BLACK,
            RGB.GREEN
    ),
    STOP(
            RGB.BLACK,
            RGB.RED,
            RGB.BLACK
    );
    private final RGB l0;
    private final RGB l1;
    private final RGB l2;

    public static MainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        if (state.isStop())
            return MainSignalAspect.STOP;

        return state.getMaxProceedSpeed() > 40 ? PROCEED_80 : PROCEED_40;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        l0.apply(states[0]);
        l1.apply(states[1]);
        if (states.length == 3)
            l2.apply(states[2]);
    }
}
