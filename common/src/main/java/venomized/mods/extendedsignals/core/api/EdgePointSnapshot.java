package venomized.mods.extendedsignals.core.api;

import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.points.IExtendedEdgePoint;

public record EdgePointSnapshot<T>(T point, double distance, boolean primary) {
}
