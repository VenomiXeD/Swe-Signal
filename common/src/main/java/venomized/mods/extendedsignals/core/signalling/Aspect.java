package venomized.mods.extendedsignals.core.signalling;

import venomized.mods.extendedsignals.core.blockentity.SignalLighting;

import java.util.List;
import java.util.Set;

public record Aspect(Set<String> litLights, Set<String> blinkingLights, int flashInterval) implements ISignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLighting states) {
        for (String litLight : litLights) {
            states.powered(litLight);
        }
        for (String blinkingLight : blinkingLights) {
            if (totalTicksForBlockEntity % 20 > flashInterval)
                states.powered(blinkingLight);
        }
    }
}
