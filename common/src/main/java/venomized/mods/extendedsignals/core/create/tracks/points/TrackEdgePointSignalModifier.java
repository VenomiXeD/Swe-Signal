package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import lombok.Getter;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedSignalBoundary;
import venomized.mods.extendedsignals.core.create.tracks.ISignalModifier;

public abstract class TrackEdgePointSignalModifier<T extends TrackEdgePoint> extends TrackEdgePoint implements IExtendedSignalBoundary<T>, ISignalModifier {
    private final static String TAG_MODIFIER_DIRECTION = "direction";

    @Getter
    // Direction must be stored in the edgepoint itself, since otherwise it's stored in the block entity
    private Direction.AxisDirection direction;

    public boolean isAligned(boolean primary) {
        return (primary ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE) == direction;
    }

    @Override
    public void write(CompoundTag nbt, DimensionPalette dimensions) {
        super.write(nbt, dimensions);
        NBTHelper.writeEnum(nbt, TAG_MODIFIER_DIRECTION, getDirection());
    }

    /**
     * @param nbt
     * @param migration
     * @param dimensions
     */
    @Override
    public void read(CompoundTag nbt, boolean migration, DimensionPalette dimensions) {
        super.read(nbt, migration, dimensions);
        direction = NBTHelper.readEnum(nbt, TAG_MODIFIER_DIRECTION, Direction.AxisDirection.class);
    }


    /**
     * @return
     */
    @Override
    public boolean shouldApply() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean canMerge() {
        return false;
    }

    /**
     * @param blockEntity
     * @param front
     */
    @Override
    public void blockEntityAdded(BlockEntity blockEntity, boolean front) {
        this.direction = front ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE;
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
