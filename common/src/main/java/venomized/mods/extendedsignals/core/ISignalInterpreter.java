package venomized.mods.extendedsignals.core;

import venomized.mods.extendedsignals.core.signalling.ISignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public interface ISignalInterpreter<T extends ISignalAspect> {
    T interpret(RawSignalState rawState);
}
