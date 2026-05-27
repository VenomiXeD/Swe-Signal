package venomized.mods.extendedsignals.core;

import org.jetbrains.annotations.NotNull;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public interface ISignalInterpreter<T extends ISignalAspect> {
    @NotNull
    T interpret(SignalStateNode state);
}
