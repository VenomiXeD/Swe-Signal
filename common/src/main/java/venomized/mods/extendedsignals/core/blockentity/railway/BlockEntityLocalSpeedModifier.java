package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.dynamic.PointModifierProperties;
import venomized.mods.extendedsignals.core.blockentity.dynamic.TextProperty;
import venomized.mods.extendedsignals.core.create.tracks.points.CoreEdgePoints;
import venomized.mods.extendedsignals.core.create.tracks.points.LineSpeedModifier;
import venomized.mods.extendedsignals.core.create.tracks.points.LocalSpeedModifier;

import java.util.List;

public class BlockEntityLocalSpeedModifier extends BlockEntityPointModifier<LocalSpeedModifier> {
    public BlockEntityLocalSpeedModifier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @param properties
     */
    @Override
    protected void attachProperties(PointModifierProperties properties) {
        properties.addProperty("path_key", new TextProperty(Component.translatable("screens.extended_signals_de.pointconfig.path_key.label"), Component.translatable("screens.extended_signals_de.pointconfig.path_key.tooltip")));
        properties.addProperty("speed", new TextProperty(Component.translatable("screens.extended_signals_de.pointconfig.speed.label"), Component.translatable("screens.extended_signals_de.pointconfig.speed.tooltip")));
    }

    /**
     * @return
     */
    @Override
    protected EdgePointType<LocalSpeedModifier> edgePointType() {
        return CoreEdgePoints.LOCAL_SPEED_MODIFIER;
    }

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
    }

    /**
     * @param blockEntity
     * @param transform
     */
    @Override
    public void transform(BlockEntity blockEntity, StructureTransform transform) {
        edgePoint.transform(blockEntity, transform);
    }
}
