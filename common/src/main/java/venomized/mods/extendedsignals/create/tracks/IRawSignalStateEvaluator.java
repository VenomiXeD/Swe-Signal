package venomized.mods.extendedsignals.create.tracks;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.RawSignalState;

import javax.annotation.Nullable;

public interface IRawSignalStateEvaluator {
    RawSignalState computeRawSignalState(
            Direction.AxisDirection axisDirection,
            @Nullable RawSignalState upcomingSignal,
            boolean reserved);
}
