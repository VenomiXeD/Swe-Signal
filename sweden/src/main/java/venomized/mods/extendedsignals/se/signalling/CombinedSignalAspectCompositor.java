package venomized.mods.extendedsignals.se.signalling;

import lombok.RequiredArgsConstructor;
import net.minecraftforge.api.distmarker.Dist;
import venomized.mods.extendedsignals.core.SignalLightState;
import venomized.mods.extendedsignals.core.signalling.ICombinedSignalAspect;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import java.util.Arrays;

public record CombinedSignalAspectCompositor(
        MainSignalAspect main,
        DistantSignalAspect distant
) implements ICombinedSignalAspect {

    /**
     * @param totalTicksForBlockEntity
     * @param states
     */
    @Override
    public void applyAspect(long totalTicksForBlockEntity, SignalLightState[] states) {
        main.applyAspect(
                totalTicksForBlockEntity,
                Arrays.copyOfRange(states, 0, 3)
        );
        if (main == MainSignalAspect.PROCEED_40 || main == MainSignalAspect.STOP && distant != DistantSignalAspect.NONE)
            return;

        distant.applyAspect(
                totalTicksForBlockEntity,
                Arrays.copyOfRange(states, 2, 5)
        );
    }

    public static CombinedSignalAspectCompositor interpret(RawSignalState state) {
        return new CombinedSignalAspectCompositor(
                MainSignalAspect.interpret(state),
                DistantSignalAspect.interpret(state)
        );
    }
}
