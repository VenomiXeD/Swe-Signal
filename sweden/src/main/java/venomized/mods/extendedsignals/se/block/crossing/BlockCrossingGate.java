package venomized.mods.extendedsignals.se.block.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.se.block.BlockGenericRotateableBlock;
import venomized.mods.extendedsignals.se.blockentity.SwedenBlockEntities;
import venomized.mods.extendedsignals.se.blockentity.crossing.BlockEntityCrossingGate;

public class BlockCrossingGate extends BlockGenericRotateableBlock implements EntityBlock {
    public BlockCrossingGate(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SwedenBlockEntities.CROSSING_GATE.create(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() && pBlockEntityType == SwedenBlockEntities.CROSSING_GATE.get() ?
                ((level, blockPos, blockState, t) -> BlockEntityCrossingGate.clientTick(level, blockPos, blockState, (BlockEntityCrossingGate) t))
                : null;
    }
}
