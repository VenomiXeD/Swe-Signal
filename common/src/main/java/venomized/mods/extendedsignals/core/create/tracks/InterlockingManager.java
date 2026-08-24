package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.Create;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.createmod.catnip.data.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import venomized.mods.extendedsignals.core.mixin.MixinSignalEdgeGroupAccessor;

import java.util.Map;
import java.util.UUID;

public class InterlockingManager {
    public static final Map<UUID, InterlockedSignalReservation> groupOwnerships = new Object2ObjectLinkedOpenHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(InterlockingManager.class);

    public static void flushReservations() {
        // LOGGER.info("Clearing {} reservation entries", groupOwnerships.size());
        groupOwnerships.clear();
    }

    public static ReservationResult tryReserveChain(Train train, Map<UUID, Pair<SignalBoundary, Boolean>> groups) {
        // for (UUID group : groups) {
        //     ReservationResult result = trainOwnsGroup(train, group);
        //     if (result == ReservationResult.CONFLICT)
        //         return ReservationResult.CONFLICT;
        // }
        groups.forEach((uuid, signalBoundaryBooleanPair) -> {
            groupOwnerships.put(uuid, new InterlockedSignalReservation(
                    train.id, signalBoundaryBooleanPair.getFirst(),
                    0
            ));
        });
        // LOGGER.info("Train {} reserved group of size {}", train.id, groups.size());

        return ReservationResult.OWNED;
    }

    public static ReservationResult trainOwnsGroup(Train self, SignalEdgeGroup group) {
        if (group == null)
            return ReservationResult.NONE;
        InterlockedSignalReservation interlockedSignalReservation = groupOwnerships.get(group.id);
        if (interlockedSignalReservation == null)
            return ReservationResult.NONE;
        return interlockedSignalReservation.train().equals(self.id) ? ReservationResult.OWNED : ReservationResult.CONFLICT;
    }

    //
    public static ReservationResult trainOwnsGroup(Train self, UUID group) {
        if (group == null)
            return ReservationResult.NONE;
        return trainOwnsGroup(self, Create.RAILWAYS.signalEdgeGroups.get(group));
    }

    public static ReservationResult trainOwnsGroupIntersecting(Train self, SignalEdgeGroup group) {
        if (group == null)
            return ReservationResult.NONE;
        ReservationResult direct = trainOwnsGroup(self, group);
//
        if (direct != ReservationResult.NONE) {
            return direct; // OWNED or CONFLICT
        }
//
        if (group.intersectingResolved.isEmpty()) {
            ((MixinSignalEdgeGroupAccessor) group).extendedSignals$walkIntersecting(group.intersectingResolved::add);
        }
//
        boolean ownsAny = false;
        for (SignalEdgeGroup intersecting : group.intersectingResolved) {
            ReservationResult result = trainOwnsGroup(self, intersecting);
            switch (result) {
                case OWNED -> ownsAny = true;
                case CONFLICT -> {
                    return ReservationResult.CONFLICT;
                }
            }
        }
//
        return ownsAny ? ReservationResult.OWNED : ReservationResult.NONE;
    }

    public static InterlockingManager.ReservationResult trainOwnsGroupIntersecting(Train self, UUID signalEdgeGroupId) {
        if (signalEdgeGroupId == null)
            return InterlockingManager.ReservationResult.NONE;

        return trainOwnsGroupIntersecting(self, Create.RAILWAYS.signalEdgeGroups.get(signalEdgeGroupId));
    }

    public static void clearReservationsForTrain(Train train) {
        groupOwnerships.values().removeIf(next -> next.train().equals(train.id));
        //LOGGER.info("Cleared all reservations for {} ({})", train.id, train.name.getString());
    }

    public static void clearReservationForTrain(Train self, SignalEdgeGroup group) {
        if (group == null)
            return;

        boolean removed = groupOwnerships.entrySet().removeIf(
                entry -> entry.getKey().equals(group.id) && entry.getValue().train.equals(self.id)
        );

        // if (removed) {
        //     //LOGGER.info("Cleared reservation for {} ({})", self.id, group.id);
        // }
    }

    public static void clearReservationForTrain(Train self, UUID groupId) {
        clearReservationForTrain(self, Create.RAILWAYS.signalEdgeGroups.get(groupId));
    }

    public enum ReservationResult {
        NONE,
        OWNED,
        CONFLICT
    }

    public record InterlockedSignalReservation(
            UUID train,
            SignalBoundary boundary,
            int priority
    ) {
    }
}
