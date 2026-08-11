package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalLighting;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;


public enum HvMainSignalAspect implements IMainSignalAspect {
    PROCEED("hp_proceed"),
    PROCEED_40("hp_proceed", "hp_reduction"),
    STOP("hp_stop_left", "hp_stop_right");
    private final String[] litLights;

    HvMainSignalAspect(String... litLights) {
        this.litLights = litLights;
    }

    public static HvMainSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        if (state.isStop())
            return HvMainSignalAspect.STOP;

        return state.getMaxProceedSpeed() <= 40
                ? HvMainSignalAspect.PROCEED_40
                : HvMainSignalAspect.PROCEED;
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
