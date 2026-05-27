package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.EdgePointType;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

import java.util.UUID;

public class SpeedModifier extends TrackEdgePointSignalModifier<SpeedModifier> {
    public static final EdgePointType<SpeedModifier> SPEED_MODIFIER = EdgePointType.register(
            ExtendedSignalsCore.res("speed_modifier"), SpeedModifier::new
    );
    private final static String TAG_WRENCHED_NAME = "wrenched";
    private boolean discard;

    /**
     * @return
     */
    @Override
    public boolean canMerge() {
        return false;
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
    public boolean doSkipChaining(boolean front, Train train) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public UUID boundaryId() {
        return null;
    }

    /**
     * @param stateToBeModified
     * @return
     */
    @Override
    public void applyModifier(SignalStateNode stateToBeModified) {
        stateToBeModified.setMaxProceedSpeed(40);
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
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, DimensionPalette dimensions) {
        super.write(nbt, dimensions);
        nbt.putBoolean(TAG_WRENCHED_NAME, discard);
    }

    /**
     * @param nbt
     * @param migration
     * @param dimensions
     */
    @Override
    public void read(CompoundTag nbt, boolean migration, DimensionPalette dimensions) {
        super.read(nbt, migration, dimensions);
        discard = nbt.getBoolean(TAG_WRENCHED_NAME);
    }


    public void onWrenched() {
        discard = !discard;
    }
}
