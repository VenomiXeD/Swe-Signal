package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.api.contraption.transformable.TransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.signal.TrackEdgePoint;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.dynamic.BoolProperty;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.create.tracks.points.IConfigurableEdgePoint;
import venomized.mods.extendedsignals.core.create.tracks.points.TrackEdgePointSignalModifier;

import java.util.List;

public abstract class BlockEntityPointModifier<T extends TrackEdgePoint> extends SmartBlockEntity implements TransformableBlockEntity {
    protected TrackTargetingBehaviour<T> edgePoint;

    @Getter
    private PointModifierProperties properties;

    public BlockEntityPointModifier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        PointModifierProperties defaultProperties = new PointModifierProperties();
        defaultProperties.addProperty("discard", new BoolProperty(Component.translatable("screens.extended_signals_de.pointconfig.discard.label"), Component.translatable("screens.extended_signals_de.pointconfig.discard.tooltip")));
        defaultProperties.addProperty("forced", new BoolProperty(Component.translatable("screens.extended_signals_de.pointconfig.forced.label"), Component.translatable("screens.extended_signals_de.pointconfig.forced.tooltip")));

        attachProperties(defaultProperties);

        properties = defaultProperties;
    }

    protected abstract void attachProperties(PointModifierProperties properties);

    public void refreshPointProperties() {
        if (edgePoint.getEdgePoint() instanceof IConfigurableEdgePoint point) {
            point.refreshPointProperties(getProperties());
        }
    }

    protected abstract EdgePointType<T> edgePointType();

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, edgePointType()));
    }

    public void transform(BlockEntity blockEntity, StructureTransform transform) {
        edgePoint.transform(blockEntity, transform);
    }

    /**
     * @param tag
     * @param registries
     * @param clientPacket
     */
    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.put("properties", getProperties().toNBT());
    }

    /**
     * @param tag
     * @param registries
     * @param clientPacket
     */
    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        getProperties().fromNBT(tag.getCompound("properties"));
    }
}
