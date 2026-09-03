package venomized.mods.extendedsignals.se.signaling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum DwarfSignalAspect implements ISignalAspect {
    PROCEED(
            true,
            false,
            false,
            true
    ),
    PROCEED_OBSTACLE(
            false,
            true,
            false,
            true
    ),
    PROCEED_UNKNOWN(
            true,
            false,
            true,
            false
    ),
    STOP(
            false,
            false,
            true,
            true
    );

    private final boolean l0Lit;
    private final boolean l1Lit;
    private final boolean l2Lit;
    private final boolean l3Lit;

    public static DwarfSignalAspect interpret(SignalStateNode node, Direction.AxisDirection direction) {
        if (node.isStop()) {
            return STOP;
        }

        if (node.isProceed())
            return PROCEED;

        return PROCEED_UNKNOWN;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float totalTicksForBlockEntity, SignalContainer states) {
        if (l0Lit)
            states.powered("l0");
        if (l1Lit)
            states.powered("l1");
        if (l2Lit)
            states.powered("l2");
        if (l3Lit)
            states.powered("l3");
    }
}
