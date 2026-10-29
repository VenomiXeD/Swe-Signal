package venomized.mods.extendedsignals.hu.signalling;

import lombok.RequiredArgsConstructor;
import venomized.mods.extendedsignals.core.blockentity.LightContainer;
import venomized.mods.extendedsignals.core.signalling.Aspect;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;

@RequiredArgsConstructor
public enum Interlocked2Signal implements IMainSignalAspect {
    STOP(new Aspect(List.of("l0"), null)),
    PROCEED(new Aspect(List.of("l1"), null));

    private final Aspect aspect;

    /**
     * @param seconds
     * @param states
     */
    @Override
    public void applyAspect(float seconds, LightContainer states) {
        aspect.applyAspect(seconds, states);
    }

    public static Interlocked2Signal interpret(SignalStateNode state) {
        return state.isStop() ? STOP : PROCEED;
    }
}
