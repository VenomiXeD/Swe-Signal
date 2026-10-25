package venomized.mods.extendedsignals.hu.signalling;

import lombok.RequiredArgsConstructor;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.*;

import java.util.List;

@RequiredArgsConstructor
public enum ABS3Signal implements IMainSignalAspect, IDistantSignalAspect {
    CLEAR(new Aspect(List.of("l0"), null)),
    EXPECT_STOP(new Aspect(List.of("l2"), null)),
    STOP(new Aspect(List.of("l1"), null));

    private final Aspect aspect;

    /**
     * @param seconds
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalContainer states) {
        aspect.applyAspect(seconds, states);
    }

    public static ABS3Signal interpret(SignalStateNode state) {
        if (state.isStop())
            return STOP;

        if (state.getNextState() != null && state.getNextState().isStop())
            return EXPECT_STOP;

        return CLEAR;
    }
}
