package venomized.mc.mods.swsignals.block.se.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.BlockRailroadCrossingObject;
import venomized.mc.mods.swsignals.blockentity.SwBlockEntities;

public class BlockThreeLightCrossingSignal extends BlockRailroadCrossingObject {
    public BlockThreeLightCrossingSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SwBlockEntities.BE_THREE_LIGHT_CROSSING_LIGHT_SIGNAL.create(blockPos, blockState);
    }
}
