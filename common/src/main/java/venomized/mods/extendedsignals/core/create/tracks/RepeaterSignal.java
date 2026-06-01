package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.UUID;

public class RepeaterSignal extends TrackEdgePoint implements IExtendedSignalBoundary<RepeaterSignal>, IRawSignalStateEvaluator {
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
    public boolean doSkipChaining(Direction.AxisDirection direction, Train train) {
        return true;
    }

    /**
     * @return
     */
    @Override
    public UUID pointId() {
        return null;
    }

    /**
     * @param upcomingSignal
     * @param train
     * @return
     */
    @Override
    public SignalStateNode computeSignalState(Direction.AxisDirection direction, @Nullable SignalStateNode upcomingSignal, Train train) {
        return new SignalStateNode()
                .setProceed(true);
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
