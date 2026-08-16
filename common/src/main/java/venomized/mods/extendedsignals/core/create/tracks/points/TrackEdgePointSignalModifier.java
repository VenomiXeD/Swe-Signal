package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.graph.DimensionPalette;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import it.unimi.dsi.fastutil.objects.Object2ReferenceLinkedOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.create.tracks.CollectedSignal;
import venomized.mods.extendedsignals.core.create.tracks.IExtendedEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.ISignalModifier;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public abstract class TrackEdgePointSignalModifier<T extends TrackEdgePoint> extends DirectionalEdgePoint<T> implements IExtendedEdgePoint<T>, ISignalModifier, IConfigurableEdgePoint {
    @Getter
    @Setter
    protected boolean discardMode;
    @Getter
    @Setter
    protected boolean forcedMode;

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
        nbt.putString("path_key", pathIdentifierActivationPattern);
    }

    /**
     * @param otherType
     * @param front
     * @return
     */
    @Override
    public boolean canCoexistWith(EdgePointType<?> otherType, boolean front) {
        if (otherType == EdgePointType.SIGNAL)
            return true;
        return super.canCoexistWith(otherType, front);
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
        pathIdentifierActivationPattern = nbt.getString("path_key");
    }

    @Override
    public void refreshPointProperties(PointModifierProperties properties) {
        discardMode = (boolean) properties.getProperty("discard").getValue();
        forcedMode = (boolean) properties.getProperty("forced").getValue();
        pathIdentifierActivationPattern = (String) properties.getProperty("path_key").getValue();
    }

    /**
     * @return
     */
    public ModifierAction onAction(boolean primary, List<CollectedSignal> points, Train train) {
        if (!isAligned(primary))
            return ModifierAction.NONE;

        if (discardMode)
            return ModifierAction.DISCARD;

        if (forcedMode)
            return ModifierAction.APPLY;

        return points.stream().anyMatch(e -> e.boundary() instanceof PathIdentifierEdgePoint point && Pattern.matches(pathIdentifierActivationPattern, point.getPathIdentifierKey())) ?
                ModifierAction.APPLY :
                ModifierAction.NONE;
    }

    /**
     * @return
     */
    @Override
    public boolean canMerge() {
        return true;
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

}
