package venomized.mods.extendedsignals.core.signalling;

import venomized.mods.extendedsignals.core.blockentity.SignalContainer;

import java.util.Set;

public record Aspect(Set<String> litLights, Set<String> blinkingLights, int flashInterval) implements ISignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(float seconds, SignalContainer states) {
        for (String litLight : litLights) {
            states.powered(litLight);
        }
        for (String blinkingLight : blinkingLights) {
            if (seconds % 1 > flashInterval)
                states.powered(blinkingLight);
        }
    }
}
