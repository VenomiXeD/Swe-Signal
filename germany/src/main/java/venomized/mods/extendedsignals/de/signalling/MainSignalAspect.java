package venomized.mods.extendedsignals.de.signalling;

import lombok.RequiredArgsConstructor;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum MainSignalAspect implements IMainSignalAspect {
    PROCEED(
            RGB.GREEN,
            RGB.BLACK,
            RGB.BLACK,
            RGB.BLACK,
            RGB.BLACK,
            RGB.BLACK
    ),
    PROCEED_40(
            RGB.GREEN,
            RGB.BLACK,
            RGB.BLACK,
            RGB.BLACK,
            RGB.BLACK,
            RGB.YELLOW
    ),
    STOP(
            RGB.BLACK,
            RGB.RED,
            RGB.RED,
            RGB.BLACK,
            RGB.BLACK,
            RGB.BLACK
    );

    private RGB[] colors;

    MainSignalAspect(
            RGB top,
            RGB topleft,
            RGB topright,
            RGB rightshunt,
            RGB leftshunt,
            RGB bottom
    ) {
        colors = new RGB[]{top, topleft, topright, rightshunt, leftshunt, bottom};
    }

    public static MainSignalAspect interpret(SignalStateNode state) {
        return state.isProceed()
                ? state.getMaxProceedSpeed() > 40
                  ? MainSignalAspect.PROCEED
                  : MainSignalAspect.PROCEED_40
                : MainSignalAspect.STOP;
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        for (int i = 0; i < states.length; i++) {
            colors[i].apply(states[i]);
        }
    }
}
