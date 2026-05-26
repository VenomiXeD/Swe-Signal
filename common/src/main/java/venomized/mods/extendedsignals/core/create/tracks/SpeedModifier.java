package venomized.mods.extendedsignals.core.create.tracks;

import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.EdgePointType;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.signalling.RawSignalState;
import venomized.mods.extendedsignals.core.signalling.SignalStateRemapper;

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
    public boolean skipChaining() {
        return false;
    }

    /**
     * @param stateToBeModified
     * @return
     */
    @Override
    public void applyModifier(RawSignalState stateToBeModified) {
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
