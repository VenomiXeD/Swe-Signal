package venomized.mods.extendedsignals.se.signaling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.SignalLightState;
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


    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        light(l0Lit, states[0]);
        light(l1Lit, states[1]);
        light(l2Lit, states[2]);
        light(l3Lit, states[3]);
    }

    private static void light(boolean lit, SignalLightState state) {
        (lit ? RGB.WHITE : RGB.BLACK).apply(state);
    }

    public static DwarfSignalAspect interpret(SignalStateNode node, Direction.AxisDirection direction) {
        if (node.isStop(direction)) {
            return STOP;
        }

        if (node.isProceed())
            return PROCEED;

        return PROCEED_UNKNOWN;
    }
}
