package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public enum KsMainSignalAspect implements IMainSignalAspect {
    PROCEED("proceed"),
    STOP("stop");
    private final String[] litLights;

    KsMainSignalAspect(String... lit) {
        litLights = lit;
    }

    public static @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (state.isStop())
            return STOP;

        return PROCEED;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLighting states) {
        for (String litLight : litLights) {
            states.powered(litLight);
        }
    }
}
