package venomized.mods.extendedsignals.core.create.tracks.points;

import com.simibubi.create.content.trains.graph.DimensionPalette;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.apache.commons.lang3.math.NumberUtils;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.signalling.SignalStateNode;

public class LocalSpeedModifier extends TrackEdgePointSignalModifier<LocalSpeedModifier> implements IConfigurableEdgePoint {
    @Getter
    @Setter
    private float speedModifierKph;

    /**
     * @param stateToBeModified
     * @return
     */
    @Override
    public void applyModifier(SignalStateNode stateToBeModified) {
        stateToBeModified.setMaxProceedSpeed(
                Math.min(getSpeedModifierKph(), stateToBeModified.getMaxProceedSpeed())
        );
    }

    /**
     * @param nbt
     * @param registries
     * @param dimensions
     */
    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, DimensionPalette dimensions) {
        super.write(nbt, registries, dimensions);
        nbt.putFloat("local_speed", speedModifierKph);
    }

    /**
     * @param properties
     */
    @Override
    public void refreshPointProperties(PointModifierProperties properties) {
        super.refreshPointProperties(properties);

        String speed = (String) properties.getProperty("speed").getValue();
        if (NumberUtils.isCreatable(speed)) {
            speedModifierKph = NumberUtils.createNumber(speed).floatValue();
        }
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
        speedModifierKph = nbt.getFloat("local_speed");
    }
}
