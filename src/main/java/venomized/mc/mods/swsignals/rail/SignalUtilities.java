package venomized.mc.mods.swsignals.rail;

import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.signal.SignalBlockEntity;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import com.simibubi.create.content.trains.signal.SignalEdgeGroup;
import net.minecraft.core.Direction;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class SignalUtilities {
	public static UUID getGroupForSignalForwardDirection(SignalBlockEntity signal) {
		return signal.getSignal().groups.get(selectForwardDirection(signal.edgePoint.getTargetDirection()));
	}

	public static UUID getGroupForSignalBackwardDirection(SignalBlockEntity signal) {
		return signal.getSignal().groups.get(selectBackwardDirection(signal.edgePoint.getTargetDirection()));
	}


	public static boolean selectForwardDirection(Direction.AxisDirection axisDirection) {
		return axisDirection == Direction.AxisDirection.POSITIVE;
	}

	public static boolean selectBackwardDirection(Direction.AxisDirection axisDirection) {
		return !selectForwardDirection(axisDirection);
	}

	public static SignalEdgeGroup getSignalEdgeGroupById(UUID id, boolean client) {
		return client ? CreateClient.RAILWAYS.signalEdgeGroups.getOrDefault(id, null) : Create.RAILWAYS.signalEdgeGroups.getOrDefault(id, null);
	}

	public static DistantSignalEdgeGroup recursiveGetDistantSignalEdgeGroup(UUID forwardGroup, UUID backwardGroup, int depth) {
		DistantSignalEdgeGroup temp = null;
		for (int i = depth; i > 0; i--) {
			System.out.println("recursiveGetDistantSignalEdgeGroup depth = " + depth);
			if (temp != null) {
				temp = getDistantSignalEdgeGroupUUIDD(temp.distant(), temp.previous());
				continue;
			}
			temp = getDistantSignalEdgeGroupUUIDD(forwardGroup, backwardGroup);
		}
		return temp;
	}

	/**
	 * Searches 1 block forward (theoretically reading the next, next block)
	 */
	public static DistantSignalEdgeGroup getDistantSignalEdgeGroupUUIDD(UUID forwardGroup, UUID backwardGroup) {
		Optional<TrackGraph> trackGraphForTheGroup = Create.RAILWAYS.trackNetworks.values()
				.stream()
				.filter(
						e -> e.getPoints(EdgePointType.SIGNAL)
								.stream()
								.filter(p -> p.groups.either(u -> u.equals(forwardGroup)))
								.count() > 0f)
				.findFirst();

		if (trackGraphForTheGroup.isPresent()) {
			Collection<SignalBoundary> points = trackGraphForTheGroup.get().getPoints(EdgePointType.SIGNAL);
			for (SignalBoundary point : points) {
				if (point.groups.either(u -> u.equals(forwardGroup))) {
					for (SignalEdgeGroup signalEdgeGroup : CreateClient.RAILWAYS.signalEdgeGroups.values()) {
						if (point.groups.either(e -> e.equals(signalEdgeGroup.id))) {
							if (!signalEdgeGroup.id.equals(forwardGroup)) {
								if (backwardGroup.equals(signalEdgeGroup.id)) {
									continue;
								}
								return new DistantSignalEdgeGroup(signalEdgeGroup.id, forwardGroup);
							}

						}
					}

				}
			}
		}

		return null;
	}
	public record DistantSignalEdgeGroup(UUID distant, UUID previous) {}
}


