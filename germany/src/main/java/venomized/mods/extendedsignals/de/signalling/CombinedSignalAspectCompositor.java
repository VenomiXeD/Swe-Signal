package venomized.mods.extendedsignals.de.signalling;

import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.blockentity.BlockEntitySignal;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.Arrays;

public record CombinedSignalAspectCompositor(SignalStateNode rawState) implements ICombinedSignalAspect {
    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        MainSignalAspect.interpret(rawState)
                .applyAspect(totalTicksForBlockEntity,
                        Arrays.copyOfRange(states, 0, 6)
                );

        DistantSignalAspect.interpret(rawState)
                .applyAspect(totalTicksForBlockEntity, new SignalLightState[]{
                        null, // Real Distant signal has an additional light, combined doesn't. Perhaps an abstraction for this in the future
                        states[6],
                        states[7],
                        states[8],
                        states[9]
                });
    }
}
