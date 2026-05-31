package venomized.mods.extendedsignals.core.create.tracks;

import net.minecraft.core.Direction;

public record CollectedSignal(
        IExtendedSignalBoundary<?> boundary,
        Direction.AxisDirection signalDirection,
        boolean isStoppingAtThisNode, double distance,
        double distanceFromPreviousSignal,
        ISignalModifier[] signalModifierSnapshot
) {
}
