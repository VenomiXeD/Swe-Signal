package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.ExtendedSignals;

import java.util.function.BiFunction;

public interface IAutoBlockEntityBlock {
    void setBlockEntityFactory(BiFunction<BlockPos, BlockState, BlockEntity> factory);
}
