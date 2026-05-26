package venomized.mods.extendedsignals.core;

import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public interface ISignalInterpreter<T extends ISignalAspect> {
    @NotNull
    T interpret(RawSignalState state);
}
