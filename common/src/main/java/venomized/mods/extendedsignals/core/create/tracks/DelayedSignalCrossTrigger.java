package venomized.mods.extendedsignals.core.create.tracks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;

@AllArgsConstructor
@Getter
@Setter
public final class DelayedSignalCrossTrigger {
    int remainingDelayTicks;
    boolean primary;
    IExtendedSignalBoundary<?> signalBoundary;
}
