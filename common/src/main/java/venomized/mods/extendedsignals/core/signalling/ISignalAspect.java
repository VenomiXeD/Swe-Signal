package venomized.mods.extendedsignals.core.signalling;

import venomized.mods.extendedsignals.core.SignalLightState;

public interface ISignalAspect {
    void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states);
}
