package venomized.mods.extendedsignals.core.signalling;

import venomized.mods.extendedsignals.core.blockentity.LightContainer;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * Basic container for signal aspect lighting
 */
public class Aspect implements ISignalAspect {
    private final Collection<String> litLights;
    private final Collection<String> flashingLights;
    private final float flashFrequency;
    private final float flashDutyCycle;

    /**
     * Constructs an instance of a signal aspect
     *
     * @param litLights      Steady lit lights, supplying null defaults to an empty collection
     * @param flashingLights Flashing lights, supplying null defaults to an empty collection
     * @param flashInterval  Flash interval in seconds
     */
    public Aspect(@Nullable Collection<String> litLights, @Nullable Collection<String> flashingLights, float flashFrequency, float flashDutyCycle) {
        this.litLights = litLights != null ? List.copyOf(litLights) : List.of();
        this.flashingLights = flashingLights != null ? List.copyOf(flashingLights) : List.of();
        this.flashFrequency = flashFrequency;
        this.flashDutyCycle = flashDutyCycle;
    }

    public Aspect(@Nullable Collection<String> litLights, @Nullable Collection<String> blinkingLights, float flashFrequency) {
        this(litLights, blinkingLights, flashFrequency, 0.5f);
    }

    /**
     * Constructor variant with flashing interval defaulted to 0.5f
     *
     * @param litLights
     * @param blinkingLights
     */
    public Aspect(@Nullable Collection<String> litLights, @Nullable Collection<String> blinkingLights) {
        this(litLights, blinkingLights, 60f, 0.5f);
    }

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float seconds, LightContainer states) {
        for (String litLight : litLights) {
            states.powered(litLight);
        }
        for (String blinkingLight : flashingLights) {
            if (ISignalAspect.blink(seconds, flashFrequency, flashDutyCycle))
                states.powered(blinkingLight);
        }
    }
}
