package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.TrackGraph;
import com.simibubi.create.content.trains.signal.SingleBlockEntityEdgePoint;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.server.ServerLifecycleHooks;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntityPathTrainDetector;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.UUID;

public class PathTrainDetector extends SingleBlockEntityEdgePoint implements IExtendedSignalBoundary<PathTrainDetector> {
    public int triggerDistance = 512;
    public int deactivationDelay = 5 * 20;
    private int remainingDeactivationTicks = 0;
    private boolean active = false;

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
        IExtendedSignalBoundary.super.onSignalScout(direction, newState, train, distance);
        if (distance > triggerDistance)
            return;

        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        ServerLevel level = currentServer.getLevel(getBlockEntityDimension());
        if (level.getBlockEntity(blockEntityPos) instanceof BlockEntityPathTrainDetector detector) {
            detector.trainInbound();
            active = true;
            remainingDeactivationTicks = deactivationDelay;
        }
    }

    @Override
    public void onSignalCrossedLate(Direction.AxisDirection direction, Train train) {
        active = false;
    }

    /**
     * @param graph
     * @param preTrains
     */
    @Override
    public void tick(TrackGraph graph, boolean preTrains) {
        super.tick(graph, preTrains);
        if (active)
            return;

        if (remainingDeactivationTicks == 0) {
            MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
            ServerLevel level = currentServer.getLevel(getBlockEntityDimension());
            if (level.getBlockEntity(blockEntityPos) instanceof BlockEntityPathTrainDetector detector) {
                detector.trainOutbound();
            }

            remainingDeactivationTicks = -1;
        } else {
            remainingDeactivationTicks--;
        }
    }

    /**
     * @param nbt
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, DimensionPalette dimensions) {
        super.write(nbt, dimensions);
        nbt.putInt("trigger_distance", triggerDistance);
        nbt.putInt("delay_timer", deactivationDelay);
    }

    /**
     * @param nbt
     * @param migration
     * @param dimensions
     */
    @Override
    public void read(CompoundTag nbt, boolean migration, DimensionPalette dimensions) {
        super.read(nbt, migration, dimensions);
        triggerDistance = nbt.getInt("trigger_distance");
        deactivationDelay = nbt.getInt("delay_timer");
    }

    /**
     * @return
     */
    @Override
    public UUID pointId() {
        return getId();
    }
}
