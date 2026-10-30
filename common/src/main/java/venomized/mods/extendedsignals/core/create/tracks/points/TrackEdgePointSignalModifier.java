package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.create.tracks.CollectedEdgePoint;

import java.util.List;
import java.util.regex.Pattern;

public abstract class TrackEdgePointSignalModifier<T extends TrackEdgePoint> extends DirectionalEdgePoint<T> implements ISignalStateModifier, IConfigurableEdgePoint {
    @Getter
    @Setter
    protected boolean discardMode;
    @Getter
    @Setter
    protected boolean forcedMode;

    @Getter
    @Setter
    protected boolean selfTrigger;

    @Getter
    @Setter
    protected String pathIdentifierActivationPattern = "";

    public boolean isAligned(boolean primary) {
        return primary == isFront();
    }

    /**
     * @param nbt
     * @param registries
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions) {
        super.write(nbt, registries, dimensions);
        nbt.putBoolean("discard", discardMode);
        nbt.putBoolean("forced", forcedMode);
        nbt.putBoolean("self_trigger", selfTrigger);
        nbt.putString("path_key", pathIdentifierActivationPattern);
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
        discardMode = nbt.getBoolean("discard");
        forcedMode = nbt.getBoolean("forced");
        selfTrigger = nbt.getBoolean("self_trigger");
        pathIdentifierActivationPattern = nbt.getString("path_key");
    }

    @Override
    public void refreshPointProperties(PointModifierProperties properties) {
        discardMode = (boolean) properties.getProperty("discard").getValue();
        forcedMode = (boolean) properties.getProperty("forced").getValue();
        selfTrigger = (boolean) properties.getProperty("self_trigger").getValue();
        pathIdentifierActivationPattern = (String) properties.getProperty("path_key").getValue();
    }

    /**
     * @return
     */
    public ModifierAction onAction(boolean primary, List<CollectedEdgePoint> points, Train train) {
        if (!isAligned(primary))
            return ModifierAction.NONE;

        if (discardMode)
            return ModifierAction.DISCARD;

        if (forcedMode)
            return ModifierAction.APPLY;

        for (CollectedEdgePoint point : points) {
            if (point.boundary().get() instanceof PathIdentifierEdgePoint path)
                if (Pattern.matches(pathIdentifierActivationPattern, path.getPathIdentifierKey()))
                    return ModifierAction.APPLY;
        }
        return ModifierAction.NONE;
    }
}
