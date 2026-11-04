package venomized.mods.extendedsignals.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.ExtendedSignals;

import java.util.function.BiFunction;

public interface IAutoBlockEntityBlock extends EntityBlock {
    void setBlockEntityFactory(BiFunction<BlockPos, BlockState, BlockEntity> factory);
}
