package venomized.mc.mods.swsignals.block.se.crossing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mc.mods.swsignals.block.Sw45DegreeBlock;
import venomized.mc.mods.swsignals.blockentity.se.SeBlockEntities;
import venomized.mc.mods.swsignals.blockentity.se.crossing.BlockEntityCrossingGate;

public class BlockCrossingGate extends Sw45DegreeBlock implements EntityBlock {
    public BlockCrossingGate(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SeBlockEntities.BE_CROSSING_GATE.create(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() && pBlockEntityType == SeBlockEntities.BE_CROSSING_GATE.get() ?
                ((level, blockPos, blockState, t) -> BlockEntityCrossingGate.clientTick(level, blockPos, blockState, (BlockEntityCrossingGate) t))
                : null;
    }
}
