package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

import javax.annotation.Nullable;

public interface IRawSignalStateEvaluator {
    RawSignalState computeRawSignalState(
            Direction.AxisDirection axisDirection,
            @Nullable RawSignalState upcomingSignal,
            Train train
    );
}
