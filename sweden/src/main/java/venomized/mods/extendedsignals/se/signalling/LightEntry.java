package venomized.mods.extendedsignals.se.signalling;

import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.ISignalAspect;

record LightEntry(ISignalAspect.RGB color, boolean blink) {
    void apply(long ticks, SignalLightState lightState) {
        boolean isActive = ticks % 20 > 10;

        ISignalAspect.RGB applyColor = blink ? (isActive ? color : ISignalAspect.RGB.BLACK) : color;
        applyColor.apply(lightState);
    }
}