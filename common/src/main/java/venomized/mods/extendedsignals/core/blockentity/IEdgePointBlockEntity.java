package venomized.mods.extendedsignals.core.blockentity;

import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;

public interface IEdgePointBlockEntity {
    TrackTargetingBehaviour<?> getTrackTargetingBehavior();
}
