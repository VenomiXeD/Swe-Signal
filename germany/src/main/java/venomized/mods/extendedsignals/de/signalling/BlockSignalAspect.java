package venomized.mods.extendedsignals.de.signalling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum BlockSignalAspect implements ISignalAspect {
    PROCEED(true),
    STOP(false);

    private final boolean proceed;

    public static BlockSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return state.isStop() ? STOP : PROCEED;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        if (states.length == 2) {
            (proceed ? RGB.BLACK : RGB.RED).apply(states[0]);
            (proceed ? RGB.GREEN : RGB.BLACK).apply(states[1]);
        }
    }
}
