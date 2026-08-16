package venomized.mods.extendedsignals.core.create.tracks;

import net.minecraft.core.Direction;

import java.util.List;

public record CollectedSignal(
        IExtendedEdgePoint<?> boundary,
        Direction.AxisDirection signalDirection,
        boolean isStoppingAtThisNode, double distance,
        double distanceFromPreviousSignal,
        List<ISignalModifier> signalModifierSnapshot
) {
}
