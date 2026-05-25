package venomized.mods.extendedsignals.core.create.tracks;

import net.minecraft.core.Direction;

import java.util.Collection;
import java.util.List;

public record CollectedSignal(
        IExtendedSignalBoundary<?> boundary,
        Direction.AxisDirection direction,
        double distance,
        double distanceFromPreviousSignal,
        ISignalModifier[] signalModifiers
) {

}
