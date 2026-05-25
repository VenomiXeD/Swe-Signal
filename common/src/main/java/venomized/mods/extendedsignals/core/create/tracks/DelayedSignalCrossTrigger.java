package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.SignalBoundary;
import lombok.*;
import net.minecraft.core.Direction;

@AllArgsConstructor
@Getter
@Setter
public final class DelayedSignalCrossTrigger {
    int remainingDelayTicks;
    Direction.AxisDirection direction;
    IExtendedSignalBoundary<?> signalBoundary;
}
