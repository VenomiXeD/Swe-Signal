package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import javax.annotation.Nullable;

public interface IRawSignalStateEvaluator {
    SignalStateNode computeRawSignalState(
            boolean primary,
            @Nullable SignalStateNode upcomingSignal,
            Train train
    );
}
