package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RepeaterSignalEdgePoint extends TrackEdgePoint implements ISignal<RepeaterSignalEdgePoint> {
    /**
     * @param front
     * @param mainSignal
     */
    @Override
    public void setMainSignal(boolean front, boolean mainSignal) {
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

    /**
     * @param front
     * @return
     */
    @Override
    public boolean isMainSignal(boolean front) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public Component getName() {
        return Component.translatable("train_map.extended_signals.edgepoint.repeater.name");
    }

    /**
     * @param front
     * @param blockSignalMode
     */
    @Override
    public void setBlockSignalMode(boolean front, boolean blockSignalMode) {
    }

    /**
     * @param front
     * @return
     */
    @Override
    public boolean isBlockSignalMode(boolean front) {
        return false;
    }
}
