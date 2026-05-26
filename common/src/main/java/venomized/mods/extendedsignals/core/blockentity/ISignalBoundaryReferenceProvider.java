package venomized.mods.extendedsignals.core.blockentity;

import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;

import java.util.UUID;

public interface ISignalBoundaryReferenceProvider {
    TrackTargetingBehaviour<?> getTrackTargetingBehavior();
}
