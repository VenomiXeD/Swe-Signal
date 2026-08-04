package venomized.mods.extendedsignals.core.create.tracks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public final class DelayedSignalCrossTrigger {
    int remainingDelayTicks;
    boolean primary;
    IExtendedSignalBoundary<?> signalBoundary;
}
