package venomized.mods.extendedsignals.core.create.tracks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;

@AllArgsConstructor
@Getter
@Setter
public final class DelayedSignalCrossTrigger {
    int remainingDelayTicks;
    boolean primary;
    IExtendedEdgePoint<?> signalBoundary;
}
