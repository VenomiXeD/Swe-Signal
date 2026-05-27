package venomized.mods.extendedsignals.core.create.tracks;

import net.minecraft.core.Direction;

public record CollectedSignal(
        IExtendedSignalBoundary<?> boundary,
        boolean primary,
        double distance,
        double distanceFromPreviousSignal,
        ISignalModifier[] signalModifierSnapshot
) {
}
