package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedEdgePoint;

import java.util.UUID;

public class DirectionalEdgePoint<T extends TrackEdgePoint> extends TrackEdgePoint implements IExtendedEdgePoint<T> {
    @Getter
    @Setter
    private boolean front;

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
        this.front = front;
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
     * @param direction
     * @param train
     * @return
     */
    @Override
    public boolean avoidSignalChaining(Direction.AxisDirection direction, Train train) {
        return true;
    }

    /**
     * @param nbt
     * @param registries
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions) {
        super.write(nbt, registries, dimensions);
        nbt.putBoolean("primary", front);
    }

    /**
     * @param nbt
     * @param registries
     * @param migration
     * @param dimensions
     */
    @Override
    public void read(CompoundTag nbt, HolderLookup.Provider registries, boolean migration, DimensionPalette dimensions) {
        super.read(nbt, registries, migration, dimensions);
        front = nbt.getBoolean("primary");
    }

    /**
     * @return
     */
    @Override
    public UUID pointId() {
        return this.getId();
    }
}
