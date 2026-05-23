package venomized.mods.extendedsignals.se.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import venomized.mods.extendedsignals.core.block.BlockRailroadCrossingObject;
import venomized.mods.extendedsignals.se.auxilliarysignals.BlockEntityRailroadCrossingSignal;
import venomized.mods.extendedsignals.se.blockentity.ExtendedSignalsSwedenBlockEntities;

public class BlockRailroadCrossingSignal extends BlockRailroadCrossingObject {
    public BlockRailroadCrossingSignal(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ExtendedSignalsSwedenBlockEntities.RAILROAD_CROSSING_SIGNAL.create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ExtendedSignalsSwedenBlockEntities.RAILROAD_CROSSING_SIGNAL.get() ? (level, pos, state, blockEntity) -> {
            ((BlockEntityRailroadCrossingSignal) blockEntity).tick(level, pos, state, blockEntity);
        } : null;
    }
}
