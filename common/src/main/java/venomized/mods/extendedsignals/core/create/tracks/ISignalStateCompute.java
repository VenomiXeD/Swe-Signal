package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import javax.annotation.Nullable;

public interface ISignalStateCompute {
    SignalStateNode computeSignalState(
            Direction.AxisDirection direction,
            @Nullable SignalStateNode upcomingSignal,
            Train train
    );
}
