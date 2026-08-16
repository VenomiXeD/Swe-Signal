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
import venomized.mods.extendedsignals.core.ExtendedSignals;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedEdgePoint;

import java.util.UUID;

public class PathIdentifierEdgePoint extends DirectionalEdgePoint<PathIdentifierEdgePoint> implements IExtendedEdgePoint<PathIdentifierEdgePoint>, IConfigurableEdgePoint {
    @Getter
    @Setter
    private String pathIdentifierKey = "";

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
        nbt.putString("path_key", this.pathIdentifierKey);
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
        pathIdentifierKey = nbt.getString("path_key");
    }

    /**
     * @param properties
     */
    @Override
    public void refreshPointProperties(PointModifierProperties properties) {
        pathIdentifierKey = (String) properties.getProperty("path_key").getValue();
    }
}
