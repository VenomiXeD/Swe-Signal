package venomized.mods.extendedsignals.se.signaling.tgoj;

import lombok.RequiredArgsConstructor;
import venomized.mods.extendedsignals.core.blockentity.LightContainer;
import venomized.mods.extendedsignals.core.signalling.Aspect;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;


@RequiredArgsConstructor
public enum TGOJBlockSignalAspect implements IMainSignalAspect {
    STOP(new Aspect(List.of("main_red"), null)),
    PROCEED_CAUTION_EXPECT_STOP(new Aspect(null, List.of("main_green"), 80f)),
    PROCEED_EXPECT_PROCEED(new Aspect(List.of("main_green"), null));

    private final Aspect litLight;

    /**
     * @param seconds
     * @param states
     */
    @Override
    public void applyAspect(float seconds, LightContainer states) {
        litLight.applyAspect(seconds, states);
    }

    public static TGOJBlockSignalAspect interpret(SignalStateNode state) {
        if (state.isStop())
            return STOP;
        if (state.getNextState() != null && state.getNextState().isStop())
            return PROCEED_CAUTION_EXPECT_STOP;
        return PROCEED_EXPECT_PROCEED;
    }
}
