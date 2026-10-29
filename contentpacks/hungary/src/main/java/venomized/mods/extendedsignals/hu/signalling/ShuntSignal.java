package venomized.mods.extendedsignals.hu.signalling;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.blockentity.SignalContainer;
import venomized.mods.extendedsignals.core.signalling.IDwarfSignalAspect;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

@RequiredArgsConstructor
public enum ShuntSignal implements ISignalAspect {
    PROCEED("white"),
    STOP("blue");

    private final String litLight;


    public static @NotNull ISignalAspect interpret(SignalStateNode state) {
        if (state.isShuntingPermitted())
            return PROCEED;
        return STOP;
    }

    /**
     * @param seconds
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalContainer states) {
        states.powered(litLight);
    }
}
