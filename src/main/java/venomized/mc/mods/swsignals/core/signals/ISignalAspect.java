package venomized.mc.mods.swsignals.core.signals;

public interface ISignalAspect {
    default double getCustomSpeedLimitationPercentage() {
        return 0d;
    }

    default boolean isCustomSpeedLimitation() {
        return getCustomSpeedLimitationPercentage() > 0d;
    }

    double getSpeedLimitationPercentage();

    SignalDisplayPattern getSignalDisplayPattern();
}
