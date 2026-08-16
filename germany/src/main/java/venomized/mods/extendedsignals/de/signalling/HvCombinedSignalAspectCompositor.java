package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public record HvCombinedSignalAspectCompositor(SignalStateNode rawState,
                                               Direction.AxisDirection direction) implements ICombinedSignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float totalTicksForBlockEntity, SignalLighting states) {
        HvMainSignalAspect.interpret(rawState, direction)
                .applyAspect(totalTicksForBlockEntity, states);

        HvDistantSignalAspect.interpret(rawState, direction)
                .applyAspect(totalTicksForBlockEntity, states);
    }
}
