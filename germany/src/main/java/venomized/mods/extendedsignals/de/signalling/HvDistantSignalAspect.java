package venomized.mods.extendedsignals.de.signalling;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.IDistantSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public enum HvDistantSignalAspect implements IDistantSignalAspect {
    OFF(),
    EXPECT_STOP("vr_yellow_right", "vr_yellow_left"),
    EXPECT_PROCEED("vr_green_right", "vr_green_left"),
    EXPECT_PROCEED_REDUCED_SPEED("vr_green_right", "vr_yellow_left");
    private final String[] litLights;

    HvDistantSignalAspect(String... litLights) {
        this.litLights = litLights;
    }

    public static HvDistantSignalAspect interpret(SignalStateNode state, Direction.AxisDirection direction) {
        SignalStateNode distant = state.getNextState();
        if (distant == null || state.isStop())
            return HvDistantSignalAspect.OFF;

        return !distant.isStop()
                ? distant.getMaxProceedSpeed() <= 40
                ? HvDistantSignalAspect.EXPECT_PROCEED_REDUCED_SPEED : HvDistantSignalAspect.EXPECT_PROCEED
                : HvDistantSignalAspect.EXPECT_STOP;
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

    /*
     return new SignalLighting()
               .withLight("vr_braking",SignalLight.whiteLight(5.25d / 16d, 99.5d / 16d, -6.5d / 16d, 1.5f, 1.5f, 0.5f))
               .withLight("vr_yellow_right", SignalLight.yellowLight(-2.25 / 16d, 99d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
               .withLight("vr_green_right", SignalLight.greenLight(-6.25 / 16d, 99d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
               .withLight("vr_yellow_left", SignalLight.yellowLight(6 / 16d, 89.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
               .withLight("vr_green_left",SignalLight.greenLight(2 / 16d, 89.5d / 16d, -6.65d / 16d, 2.75f, 2.75f, 0.5f))
     */
}
