package venomized.mods.extendedsignals.se.signaling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum MainSignalAspect implements IMainSignalAspect {
    PROCEED_80(
            true,
            false,
            false
    ),
    PROCEED_40(
            true,
            false,
            true
    ),
    STOP(
            false,
            true,
            false
    );
    private final boolean l0Lit;
    private final boolean l1Lit;
    private final boolean l2Lit;

    public static MainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        if (state.isStop())
            return MainSignalAspect.STOP;

        return state.getMaxProceedSpeed() >= 80 ? PROCEED_80 : PROCEED_40;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalContainer states) {
        if (l0Lit)
            states.powered("l0");
        if (l1Lit)
            states.powered("l1");
        if (l2Lit)
            states.powered("l2");
    }
}
