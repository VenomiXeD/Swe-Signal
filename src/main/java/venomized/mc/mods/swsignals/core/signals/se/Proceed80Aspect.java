package venomized.mc.mods.swsignals.core.signals.se;

import venomized.mc.mods.swsignals.core.signals.ISignalAspect;
import venomized.mc.mods.swsignals.core.signals.SignalDisplayPattern;

public class Proceed80Aspect implements ISignalAspect {
    @Override
    public double getSpeedLimitationPercentage() {
        return 0;
    }

    @Override
    public double getCustomSpeedLimitationPercentage() {
        return 1.0d;
    }

    @Override
    public SignalDisplayPattern getSignalDisplayPattern() {
        return null;
    }
}
