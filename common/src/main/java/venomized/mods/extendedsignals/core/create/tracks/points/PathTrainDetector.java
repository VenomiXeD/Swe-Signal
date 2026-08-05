package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityTrainPathObserver;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.UUID;

public class PathTrainDetector extends SingleBlockEntityEdgePoint implements IExtendedSignalBoundary<PathTrainDetector> {
    public int triggerDistance = 512;
    private boolean trainInbound = false;

    /**
     * @param blockEntity
     * @param front
     */
    @Override
    public void blockEntityAdded(BlockEntity blockEntity, boolean front) {
        super.blockEntityAdded(blockEntity, front);
    }

    /**
     * @param direction
     * @param train
     * @return
     */
    @Override
    public boolean doSkipChaining(Direction.AxisDirection direction, Train train) {
        return true;
    }
    /**
     * @param direction
     * @param newState
     * @param train
     * @param distance
     */
    @Override
    public void onSignalScout(Direction.AxisDirection direction, SignalStateNode newState, Train train, double distance) {
        if (distance > triggerDistance)
            return;

        trainInbound = true;
    }

    @Override
    public void onSignalCrossedLate(Direction.AxisDirection direction, Train train) {
        trainInbound = false;
    }

    /**
     * @param nbt
     * @param registries
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions) {
        super.write(nbt, registries, dimensions);
        nbt.putInt("trigger_distance", triggerDistance);
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
        triggerDistance = nbt.getInt("trigger_distance");
    }

    /**
     * @return
     */
    @Override
    public UUID pointId() {
        return getId();
    }

    public boolean trainPresent() {
        return trainInbound;
    }
}
