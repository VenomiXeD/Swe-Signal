package venomized.mods.extendedsignals.core.create.tracks;

import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.points.ISignalStateModifier;

import java.lang.ref.WeakReference;
import java.util.List;

public record CollectedEdgePoint(
        WeakReference<IExtendedEdgePoint> boundary,
        Direction.AxisDirection signalDirection,
        boolean obstacle,
        double distance,
        List<WeakReference<ISignalStateModifier>> signalModifierSnapshot
) {
}
