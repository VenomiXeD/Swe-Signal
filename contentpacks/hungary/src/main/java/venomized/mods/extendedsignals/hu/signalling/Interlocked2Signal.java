package venomized.mods.extendedsignals.hu.signalling;

import it.unimi.dsi.fastutil.objects.ObjectCollections;
import lombok.RequiredArgsConstructor;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.Aspect;
import venomized.mods.extendedsignals.core.signalling.IMainSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;
import java.util.Set;

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
    public void applyAspect(float seconds, SignalContainer states) {
        aspect.applyAspect(seconds, states);
    }

    public static Interlocked2Signal interpret(SignalStateNode state) {
        return state.isStop() ? STOP : PROCEED;
    }
}
