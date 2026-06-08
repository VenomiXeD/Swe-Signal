package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.UUID;

public class SpeedModifier extends TrackEdgePointSignalModifier<SpeedModifier> {
    private final static String TAG_WRENCHED_NAME = "wrenched";
    private final static String TAG_SPEED_NAME = "speed_setting";
    private boolean discard;
    @Getter
    @Setter
    private int speedModifierKph;

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
        return false;
    }

    /**
     * @return
     */
    @Override
    public UUID pointId() {
        return this.getId();
    }

    /**
     * @param stateToBeModified
     * @return
     */
    @Override
    public void applyModifier(SignalStateNode stateToBeModified) {
        stateToBeModified.setMaxProceedSpeed(speedModifierKph);
    }

    /**
     * @return
     */
    @Override
    public boolean shouldApply() {
        return !discard;
    }

    /**
     * @param nbt
     * @param registries
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions) {
        super.write(nbt, registries, dimensions);
        nbt.putBoolean(TAG_WRENCHED_NAME, discard);
        nbt.putInt(TAG_SPEED_NAME, speedModifierKph);
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
        discard = nbt.getBoolean(TAG_WRENCHED_NAME);
        speedModifierKph = nbt.getInt(TAG_SPEED_NAME);
    }

    public void onWrenched() {
        discard = !discard;
    }
}
