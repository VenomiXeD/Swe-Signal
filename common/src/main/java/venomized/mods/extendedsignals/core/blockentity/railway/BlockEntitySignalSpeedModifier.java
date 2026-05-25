package venomized.mods.extendedsignals.core.blockentity.railway;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.create.tracks.SpeedModifier;

import java.util.List;

public class BlockEntitySignalSpeedModifier extends SmartBlockEntity {
    private TrackTargetingBehaviour<SpeedModifier> edgePoint;

    public BlockEntitySignalSpeedModifier(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * @param behaviours
     */
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(edgePoint = new TrackTargetingBehaviour<>(this, SpeedModifier.SPEED_MODIFIER));
    }


    public InteractionResult onWrenched() {
        edgePoint.getEdgePoint().onWrenched();
        return InteractionResult.SUCCESS;
    }


}
