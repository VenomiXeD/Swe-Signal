package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public interface IAutoBlockEntityBlock extends EntityBlock {
    void setBlockEntityType(BiFunction<BlockPos, BlockState, BlockEntity> factory);
}
