package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.blockentity.dynamic.TextProperty;
import venomized.mods.extendedsignals.core.create.tracks.points.CoreEdgePoints;
import venomized.mods.extendedsignals.core.create.tracks.points.PathIdentifierEdgePoint;

import java.util.List;

public class BlockEntityPathIdentifier extends BlockEntityPointModifier<PathIdentifierEdgePoint> {
    public BlockEntityPathIdentifier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new TrackTargetingBehaviour<>(this, CoreEdgePoints.PATH_IDENTIFIER));
    }

    /**
     * @param properties
     */
    @Override
    protected void attachProperties(PointModifierProperties properties) {
        properties.addProperty("path_key", new TextProperty("", Component.translatable("screens.extended_signals_de.pointconfig.path_key.label"), Component.translatable("screens.extended_signals_de.pointconfig.path_key.tooltip")));
    }
}
