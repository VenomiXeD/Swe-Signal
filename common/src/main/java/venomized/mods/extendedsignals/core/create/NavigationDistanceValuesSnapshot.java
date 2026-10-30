package venomized.mods.extendedsignals.core.create;

import net.createmod.catnip.data.Pair;

import javax.annotation.Nullable;
import java.util.UUID;

public record NavigationDistanceValuesSnapshot(double distanceToDestination, double distanceToSignal,
                                               @Nullable Pair<UUID, Boolean> waitingForSignal,
                                               com.simibubi.create.content.trains.entity.TravellingPoint.ITrackSelector iTrackSelector) {
}
