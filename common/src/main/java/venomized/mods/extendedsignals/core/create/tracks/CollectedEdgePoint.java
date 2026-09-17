package venomized.mods.extendedsignals.core.create.tracks;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignalStateModifier;

import java.util.List;

public record CollectedEdgePoint(
        IExtendedEdgePoint<?> boundary,
        Direction.AxisDirection signalDirection,
        boolean isStoppingAtThisNode, double distance,
        double distanceFromPreviousSignal,
        List<ISignalStateModifier> signalModifierSnapshot
) {
}
