package venomized.mods.extendedsignals.de.signalling;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum HvBlockSignalAspect implements ISignalAspect {
    PROCEED(true),
    STOP(false);

    private final boolean proceed;

    public static HvBlockSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        return state.isStop() ? STOP : PROCEED;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalLighting states) {
        if (this == PROCEED) {
            states.powered("proceed");
        } else {
            states.powered("stop");
        }
    }
}
