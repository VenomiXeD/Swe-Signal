package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.EnumSet;

public enum KsMainSignalAspect implements IMainSignalAspect {
    PROCEED("proceed"),
    STOP("stop"),
    SHUNT_OK("aux_1", "zs1");
    private final String[] litLights;

    KsMainSignalAspect(String... lit) {
        litLights = lit;
    }

    public static @NotNull IMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (state.isShuntingPermitted())
            return ISignalAspect.combo(EnumSet.of(STOP, SHUNT_OK))::applyAspect;

        if (state.isStop())
            return STOP;

        return PROCEED;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float totalTicksForBlockEntity, SignalContainer states) {
        for (String litLight : litLights) {
            states.powered(litLight);
        }
    }
}
