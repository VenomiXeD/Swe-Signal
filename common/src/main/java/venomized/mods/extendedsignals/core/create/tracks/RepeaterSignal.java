package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

public class RepeaterSignal extends TrackEdgePoint implements IExtendedSignalBoundary<RepeaterSignal>, IRawSignalStateEvaluator {
    public static final EdgePointType<RepeaterSignal> REPEATER = EdgePointType.register(
            ExtendedSignalsCore.res("repeater"), RepeaterSignal::new
    );

    /**
     * @param front
     * @param mapper
     */
    @Override
    public void setMapper(boolean front, SignalStateRemapper mapper) {
    }

    /**
     * @return
     */
    @Override
    public boolean skipChaining() {
        return true;
    }

    /**
     * @param axisDirection
     * @param upcomingSignal
     * @param train
     * @return
     */
    @Override
    public RawSignalState computeRawSignalState(boolean primary, @Nullable RawSignalState upcomingSignal, Train train) {
        return new RawSignalState()
                .setNextState(upcomingSignal)
                .setReserved(true);
    }

    /**
     * @return
     */
    @Override
    public boolean canMerge() {
        return false;
    }

    /**
     * @param level
     */
    @Override
    public void invalidate(LevelAccessor level) {

    }

    /**
     * @param blockEntity
     * @param front
     */
    @Override
    public void blockEntityAdded(BlockEntity blockEntity, boolean front) {

    }

    /**
     * @param blockEntityPos
     * @param front
     */
    @Override
    public void blockEntityRemoved(BlockPos blockEntityPos, boolean front) {
        removeFromAllGraphs();
    }


}
