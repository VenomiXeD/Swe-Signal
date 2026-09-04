package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

// This is pretty awful, but it works
public enum KsCombinedSignalAspect implements ICombinedSignalAspect {
    STOP(new String[]{"stop"}, new String[]{}),
    PROCEED(new String[]{"proceed"}, new String[]{}),
    EXPECT_REDUCED_SPEED(new String[]{}, new String[]{"proceed"}),
    EXPECT_STOP(new String[]{"danger"}, new String[]{});
    private final String[] litLights;
    private final String[] flashingLights;

    KsCombinedSignalAspect(String[] lit, String[] blinking) {
        litLights = lit;
        flashingLights = blinking;
    }

    public static @NotNull ICombinedSignalAspect interpret(SignalStateNode state, Direction.AxisDirection incomingDirection) {
        if (state.isStop())
            return STOP;

        if (state.getMiscTags().containsKey("zs3v_metal"))
            return EXPECT_REDUCED_SPEED;

        if (state.getNextState() != null) {
            if (state.getNextState().isStop())
                return EXPECT_STOP;
            if (state.getMiscTags().containsKey("local_speed") && state.getNextState().getMaxProceedSpeed() < state.getMaxProceedSpeed())
                return EXPECT_REDUCED_SPEED;
        }

        return PROCEED;
    }

    /**
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalContainer states) {
        for (String litLight : litLights) {
            states.powered(litLight);
        }

        for (String litLight : flashingLights) {
            if (seconds % 1 > 0.5f) states.powered(litLight);
        }
    }
}
