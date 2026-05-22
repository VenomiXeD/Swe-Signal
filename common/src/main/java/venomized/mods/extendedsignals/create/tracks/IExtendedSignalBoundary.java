package venomized.mods.extendedsignals.create.tracks;


import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.Direction;
import venomized.mods.extendedsignals.core.RawSignalState;

public interface IExtendedSignalBoundary {
    /**
     * {@link net.minecraft.core.Direction.AxisDirection#POSITIVE} Configuration
     *
     * @return
     */
    SignalBoundaryConfiguration extendedSignals$getPositiveSignalBoundaryConfiguration();

    /**
     * {@link net.minecraft.core.Direction.AxisDirection#NEGATIVE} Configuration
     *
     * @return
     */
    SignalBoundaryConfiguration extendedSignals$getNegativeSignalBoundaryConfiguration();

    void extendedSignal$onScout(Direction.AxisDirection direction, RawSignalState newState, Train train);

    void extendedSignal$onCrossed(Train train);
}
