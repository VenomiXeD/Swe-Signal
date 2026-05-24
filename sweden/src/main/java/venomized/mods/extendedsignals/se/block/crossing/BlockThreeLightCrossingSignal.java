package venomized.mods.extendedsignals.se.block.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockRailroadCrossingObject;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;

public class BlockThreeLightCrossingSignal extends BlockRailroadCrossingObject {
    public BlockThreeLightCrossingSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SwedenBlockEntities.THREE_LIGHT_CROSSING_LIGHT_SIGNAL.create(blockPos, blockState);
    }
}
