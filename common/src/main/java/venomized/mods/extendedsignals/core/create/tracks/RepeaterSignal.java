package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;

public class RepeaterSignal extends TrackEdgePoint implements IExtendedSignalBoundary<RepeaterSignal>, IRawSignalStateEvaluator {
    public static final EdgePointType<RepeaterSignal> REPEATER = EdgePointType.register(
            ExtendedSignalsCore.res("repeater"), RepeaterSignal::new
    );

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
    public RawSignalState computeRawSignalState(Direction.AxisDirection axisDirection, @Nullable RawSignalState upcomingSignal, Train train) {
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

    @Override
    public boolean canCoexistWith(EdgePointType<?> otherType, boolean front) {
        return otherType == getType();
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

    }


}
