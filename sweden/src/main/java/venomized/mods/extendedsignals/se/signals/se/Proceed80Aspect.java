package venomized.mods.extendedsignals.se.signals.se;


import venomized.mods.extendedsignals.se.signals.ISignalAspect;
import venomized.mods.extendedsignals.se.signals.SignalDisplayPattern;

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
