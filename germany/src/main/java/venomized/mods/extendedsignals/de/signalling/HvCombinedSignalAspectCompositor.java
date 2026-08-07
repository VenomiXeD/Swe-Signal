package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Arrays;

public record HvCombinedSignalAspectCompositor(SignalStateNode rawState,
                                               Direction.AxisDirection direction) implements ICombinedSignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        HvMainSignalAspect.interpret(rawState, direction)
                .applyAspect(totalTicksForBlockEntity, Arrays.copyOfRange(states, 0, 6));

        HvDistantSignalAspect.interpret(rawState, direction)
                .applyAspect(totalTicksForBlockEntity, Arrays.copyOfRange(states, 6, 11));
    }
}
