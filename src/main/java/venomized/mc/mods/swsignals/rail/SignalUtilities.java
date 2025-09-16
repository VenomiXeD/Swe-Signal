package venomized.mc.mods.swsignals.rail;

public class SignalUtilities {
    public static float computeLightValue(float currentValue, float partialTick, boolean lit) {
        return lit ? Math.min(1, currentValue + partialTick / 10) : Math.max(0, currentValue - partialTick / 20);
    }

    public static void computeLightValueAt(int idx, float[] lightValues, float partialTick, boolean lit) {
        lightValues[idx] = computeLightValue(lightValues[idx], partialTick, lit);
    }

    // public static UUID getGroupForSignalForwardDirection(SignalBlockEntity signal) {
    // 	return signal.getSignal().groups.get(selectForwardDirection(signal.edgePoint.getTargetDirection()));
    // }
//
    // public static UUID getGroupForSignalBackwardDirection(SignalBlockEntity signal) {
    // 	return signal.getSignal().groups.get(selectBackwardDirection(signal.edgePoint.getTargetDirection()));
    // }


    // public static boolean selectForwardDirection(Direction.AxisDirection axisDirection) {
    // 	return axisDirection == Direction.AxisDirection.POSITIVE;
    // }
//
    // public static boolean selectBackwardDirection(Direction.AxisDirection axisDirection) {
    // 	return !selectForwardDirection(axisDirection);
    // }
//
    // public static SignalEdgeGroup getSignalEdgeGroupById(UUID id, boolean client) {
    // 	return client ? CreateClient.RAILWAYS.signalEdgeGroups.getOrDefault(id, null) : Create.RAILWAYS.signalEdgeGroups.getOrDefault(id, null);
    // }
//
    // public static DistantSignalEdgeGroup recursiveGetDistantSignalEdgeGroup(UUID forwardGroup, UUID backwardGroup, int depth) {
    // 	DistantSignalEdgeGroup temp = null;
    // 	for (int i = depth; i > 0; i--) {
    // 		System.out.println("recursiveGetDistantSignalEdgeGroup depth = " + depth);
    // 		if (temp != null) {
    // 			temp = getDistantSignalEdgeGroupUUIDD(temp.distant(), temp.previous());
    // 			continue;
    // 		}
    // 		temp = getDistantSignalEdgeGroupUUIDD(forwardGroup, backwardGroup);
    // 	}
    // 	return temp;
    // }
//
    // /**
    //  * Searches 1 block forward (theoretically reading the next, next block)
    //  */
    // public static DistantSignalEdgeGroup getDistantSignalEdgeGroupUUIDD(UUID forwardGroup, UUID backwardGroup) {
    // 	Optional<TrackGraph> trackGraphForTheGroup = Create.RAILWAYS.trackNetworks.values()
    // 			.stream()
    // 			.filter(
    // 					e -> e.getPoints(EdgePointType.SIGNAL)
    // 							.stream()
    // 							.filter(p -> p.groups.either(u -> u.equals(forwardGroup)))
    // 							.count() > 0f)
    // 			.findFirst();
//
    // 	if (trackGraphForTheGroup.isPresent()) {
    // 		Collection<SignalBoundary> points = trackGraphForTheGroup.get().getPoints(EdgePointType.SIGNAL);
    // 		for (SignalBoundary point : points) {
    // 			if (point.groups.either(u -> u.equals(forwardGroup))) {
    // 				for (SignalEdgeGroup signalEdgeGroup : Create.RAILWAYS.signalEdgeGroups.values()) {
    // 					if (point.groups.either(e -> e.equals(signalEdgeGroup.id))) {
    // 						if (!signalEdgeGroup.id.equals(forwardGroup)) {
    // 							if (backwardGroup.equals(signalEdgeGroup.id)) {
    // 								continue;
    // 							}
    // 							return new DistantSignalEdgeGroup(signalEdgeGroup.id, forwardGroup);
    // 						}
//
    // 					}
    // 				}
//
    // 			}
    // 		}
    // 	}
//
    // 	return null;
    // }
//
    // public record DistantSignalEdgeGroup(UUID distant, UUID previous) {
    // }
}


